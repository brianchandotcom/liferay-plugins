AUI().use(
	'aui-base',
	'aui-dialog',
	'aui-io-plugin',
	function(A) {
		Liferay.namespace('HR');

		Liferay.HR = {
			closePopup: function() {
				var instance = this;

				var popup = instance.getPopup();

				if (popup) {
					popup.hide();
				}
			},

			displayPopup: function(url, title, data) {
				var instance = this;

				var viewportRegion = A.getBody().get('viewportRegion');

				var popup = instance.getPopup();

				popup.show();

				popup.set('title', title);
				popup.set('xy', [viewportRegion.left + 20, viewportRegion.top + 20]);

				popup.io.set('method', 'GET');
				popup.io.set('uri', url);
				popup.io.set('data', data);

				popup.io.start();
			},

			getPopup: function() {
				var instance = this;

				if (!instance._popup) {
					instance._popup = new A.Dialog(
						{
							cssClass: 'hr-portlet-sites-dialog',
							resizable: false
						}
					).plug(
						A.Plugin.IO,
						{autoLoad: false}
					).render();
				}

				return instance._popup;
			}
		};

		Liferay.on(
			'sessionExpired',
			function(event) {
				var reload = function() {
					window.location.reload();
				};

				Liferay.HR.displayPopup = reload;
			}
		);
	}
);