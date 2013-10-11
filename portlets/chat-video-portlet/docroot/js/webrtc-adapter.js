/**
 * This file is a modified version of the appRTC (reference WebRTC
 * application) "adapter.js". Its purpose is to provide a universal
 * API for WebRTC stuff while individual browsers use different APIs
 * currently.
 *
 * The license below is copied from
 * <https://code.google.com/p/webrtc/source/browse/trunk/webrtc/LICENSE>.
 *
 *
 * Copyright (c) 2011, The WebRTC project authors. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   * Neither the name of Google nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * Some code is also merged from <https://github.com/ESTOS/strophe.jingle>,
 * the license being the following:
 *
 *
 * Copyright (c) 2012-2013 ESTOS GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
var RTCPeerConnection = null;
var getUserMedia = null;
var attachMediaStream = null;
var reattachMediaStream = null;
var webRtcDetectedBrowser = null;
var webRtcDetectedVersion = null;
var webRtcSupported = false;

// mozilla chrome compat layer -- very similar to adapter.js
function setupRTC() {
	var RTC = null;
	if (navigator.mozGetUserMedia && mozRTCPeerConnection) {
		console.log('This appears to be Firefox');
		var version = parseInt(navigator.userAgent.match(/Firefox\/([0-9]+)\./)[1]);
		if (version >= 22) {
			RTC = {
				peerconnection: mozRTCPeerConnection,
				browser: 'firefox',
				getUserMedia: navigator.mozGetUserMedia.bind(navigator),
				attachMediaStream: function(element, stream) {
					element.mozSrcObject = stream;
					element.play();
				},
				createIceServer: function(ice) {
					var iceServer = null;
					var url_parts = ice.url.split(':');
					if (url_parts[0].indexOf('stun') === 0) {
						// Create iceServer with stun url.
						iceServer = {
							'url': ice.url
						};
					} else if (url_parts[0].indexOf('turn') === 0 &&
							(ice.url.indexOf('transport=udp') !== -1 ||
							ice.url.indexOf('?transport') === -1)) {
						// Create iceServer with turn url.
						// Ignore the transport parameter from TURN url.
						var turn_url_parts = ice.url.split("?");
						iceServer = {
							'url': turn_url_parts[0],
							'credential': ice.password,
							'username': ice.username
						};
					}

					return iceServer;
				},
				pc_constraints: {}
			};
			if (!MediaStream.prototype.getVideoTracks)
				MediaStream.prototype.getVideoTracks = function() { return []; };
			if (!MediaStream.prototype.getAudioTracks)
				MediaStream.prototype.getAudioTracks = function() { return []; };
			RTCSessionDescription = mozRTCSessionDescription;
			RTCIceCandidate = mozRTCIceCandidate;
		}
	} else if (navigator.webkitGetUserMedia && webkitRTCPeerConnection) {
		console.log('This appears to be Chrome');
		var version = parseInt(navigator.userAgent.match(/Chrom(e|ium)\/([0-9]+)\./)[2]);
		RTC = {
			peerconnection: webkitRTCPeerConnection,
			browser: 'chrome',
			getUserMedia: navigator.webkitGetUserMedia.bind(navigator),
			attachMediaStream: function(element, stream) {
				element.src = webkitURL.createObjectURL(stream);
			},
			createIceServer: function(ice) {
				var iceServer = null;
				var url_parts = ice.url.split(':');
				if (url_parts[0].indexOf('stun') === 0) {
					// Create iceServer with stun url.
					iceServer = {
						'url': ice.url
					};
				} else if (url_parts[0].indexOf('turn') === 0) {
					if (version < 28) {
						// For pre-M28 chrome versions use old TURN format.
						var url_turn_parts = ice.url.split("turn:");
						iceServer = {
							'url': 'turn:' + ice.username + '@' + url_turn_parts[1],
							'credential': ice.password
						};
					} else {
						// For Chrome M28 & above use new TURN format.
						iceServer = {
							'url': ice.url,
							'credential': ice.password,
							'username': ice.username
						};
					}
				}

				return iceServer;
			},
			// pc_constraints: {} // FIVE-182
			pc_constraints: {'optional': [{'DtlsSrtpKeyAgreement': 'true'}]} // enable dtls support in canary
		};
		if (navigator.userAgent.indexOf('Android') != -1) {
			RTC.pc_constraints = {}; // disable DTLS on Android
		}
		if (!webkitMediaStream.prototype.getVideoTracks) {
			webkitMediaStream.prototype.getVideoTracks = function()
			{ return this.videoTracks; };
		}
		if (!webkitMediaStream.prototype.getAudioTracks) {
			webkitMediaStream.prototype.getAudioTracks = function()
			{ return this.audioTracks; };
		}
	// New syntax of getXXXStreams method in M26.
	if (!webkitRTCPeerConnection.prototype.getLocalStreams) {
		webkitRTCPeerConnection.prototype.getLocalStreams = function() {
			return this.localStreams;
		};
		webkitRTCPeerConnection.prototype.getRemoteStreams = function() {
			return this.remoteStreams;
		};
	}
	}
	if (RTC == null) {
		try { console.log('Browser does not appear to be WebRTC-capable'); } catch (e) { }
	}

	return RTC;
}

// Set Opus as the default audio codec if it's present.
function preferOpus(sdp) {
	var sdpLines = sdp.split('\r\n');

	// Search for m line.
	for (var i = 0; i < sdpLines.length; i++) {
		if (sdpLines[i].search('m=audio') !== -1) {
			var mLineIndex = i;
			break;
		}
	}
	if (mLineIndex === null)
		return sdp;

	// If Opus is available, set it as the default in m line.
	for (var i = 0; i < sdpLines.length; i++) {
		if (sdpLines[i].search('opus/48000') !== -1) {
			var opusPayload = extractSdp(sdpLines[i], /:(\d+) opus\/48000/i);
			if (opusPayload)
				sdpLines[mLineIndex] = setDefaultCodec(sdpLines[mLineIndex], opusPayload);
			break;
		}
	}

	// Remove CN in m line and sdp.
	sdpLines = removeCN(sdpLines, mLineIndex);

	sdp = sdpLines.join('\r\n');

	return sdp;
}

// Set Opus in stereo if stereo is enabled.
function addStereo(sdp) {
	var sdpLines = sdp.split('\r\n');

	// Find opus payload.
	for (var i = 0; i < sdpLines.length; i++) {
		if (sdpLines[i].search('opus/48000') !== -1) {
			var opusPayload = extractSdp(sdpLines[i], /:(\d+) opus\/48000/i);
			break;
		}
	}

	// Find the payload in fmtp line.
	for (var i = 0; i < sdpLines.length; i++) {
		if (sdpLines[i].search('a=fmtp') !== -1) {
			var payload = extractSdp(sdpLines[i], /a=fmtp:(\d+)/ );
			if (payload === opusPayload) {
				var fmtpLineIndex = i;
				break;
			}
		}
	}

	// No fmtp line found.
	if (fmtpLineIndex === null)
	return sdp;

	// Append stereo=1 to fmtp line.
	sdpLines[fmtpLineIndex] = sdpLines[fmtpLineIndex].concat(' stereo=1');

	sdp = sdpLines.join('\r\n');
	return sdp;
}

function extractSdp(sdpLine, pattern) {
	var result = sdpLine.match(pattern);
	return (result && result.length == 2)? result[1]: null;
}

// Set the selected codec to the first in m line.
function setDefaultCodec(mLine, payload) {
	var elements = mLine.split(' ');
	var newLine = new Array();
	var index = 0;
	for (var i = 0; i < elements.length; i++) {
		if (index === 3) // Format of media starts from the fourth.
			newLine[index++] = payload; // Put target payload to the first.
		if (elements[i] !== payload)
			newLine[index++] = elements[i];
	}

	return newLine.join(' ');
}

// Strip CN from sdp before CN constraints is ready.
function removeCN(sdpLines, mLineIndex) {
	var mLineElements = sdpLines[mLineIndex].split(' ');
	// Scan from end for the convenience of removing an item.
	for (var i = sdpLines.length-1; i >= 0; i--) {
		var payload = extractSdp(sdpLines[i], /a=rtpmap:(\d+) CN\/\d+/i);
		if (payload) {
			var cnPos = mLineElements.indexOf(payload);
			if (cnPos !== -1) {
				// Remove CN payload from m line.
				mLineElements.splice(cnPos, 1);
			}
			// Remove CN line in sdp
			sdpLines.splice(i, 1);
		}
	}

	sdpLines[mLineIndex] = mLineElements.join(' ');

	return sdpLines;
}
