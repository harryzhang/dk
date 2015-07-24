/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.toolbar = [
                    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
	              	{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
	              	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
	              	{ name: 'insert', items: [ 'Image', 'Flash', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
	              	{ name: 'tools', items: [  'Source' ,'Maximize'] },
	              	{ name: 'others', items: [ '-' ] },
	              	'/',
	              	
	              	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl' ] },
	              	{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
	              	

	              	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: ['ShowBlocks', '-', 'Preview', '-', 'Templates' ] },
	              	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
	              	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
	              ];

	              // Toolbar groups configuration.
	              config.toolbarGroups = [
                    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
                    { name: 'styles' },
					{ name: 'colors' },
					{ name: 'insert' },
					{ name: 'tools' },
					{ name: 'others' },
					'/',                     
	              	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
	              	{ name: 'links' },
	              	

	              	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
	              	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
	              	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
	              	{ name: 'forms' },
	              ];
};
