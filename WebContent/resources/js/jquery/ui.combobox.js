(function($) {
	$.widget( "ui.combobox", {
		_create: function() {
			var self = this,
				select = this.element.hide(),
				selected = select.children( ":selected" ),
				value = selected.val() ? selected.text() : "";
			var input = $( "<input>" )
				.insertAfter( select )
				.css({"width": "100px"})
				.val( value )
				.autocomplete({
					delay: 0,
					minLength: 0,
					source: function( request, response ) {
						select.empty();
						var url = select.attr("url");
						var bind = select.attr("bind");
						if(bind !== "undefined" && bind !== undefined){
							url += "/" + $("#" + bind).val();
						}else{
							$("select[bind]").empty();
							$(".ui-autocomplete-input").val("");
						}
						$.ajax({
							cache: false,
							url: url,
							dataType: "json",
							data: {
								rows: 12,
								name: request.term
							},
							success: function( data ) {
								response($.each(data, function(i, item) {
									select.append("<option value=\""+ item.id +"\">" + item.value+ "</option>");
									return {
										label:item.value, value: item.id, option: this
									}
								}));
							}
						});
						
						return false;
					},
					select: function( event, ui ) {
						select.val(ui.item.id);
						self._trigger( "selected", event, {
							item: ui.item
						});
					},
					change: function( event, ui ) {
						if ( !ui.item ) {
							var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
								valid = false;
							select.children( "option" ).each(function() {
								if ( this.value.match( matcher ) ) {
									this.selected = valid = true;
									return false;
								}
							});
							if ( !valid ) {
								// remove invalid value, as it didn't match anything
								$( this ).val( "" );
								select.val( "" );
								return false;
							}
						}
					}
				})
				.addClass( "ui-widget ui-widget-content ui-corner-left" );

			input.data( "autocomplete" )._renderItem = function( ul, item ) {
				ul.css({"height":"100px", "overflow-y": "auto"});
				return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + item.label + "</a>" )
					.appendTo( ul );
			};

			$( "<button>&nbsp;</button>" )
				.attr( "tabIndex", -1 )
				.attr( "title", "显示全部" )
				.insertAfter( input )
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "ui-corner-right ui-button-icon" ).css({width: "28px", height: "22px"})
				.click(function() {
					// close if already visible
					if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
						input.autocomplete( "close" );
						return false;
					}

					// pass empty string as value to search for, displaying all results
					input.autocomplete( "search", "" );
					input.focus();
					return false;
				});
		}
	});
})( jQuery );