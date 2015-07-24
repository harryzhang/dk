fis.config.merge({
	roadmap : {
		domain : {
			'**.js' : 'http://static.hehenian.com',
			'**.css' : 'http://static.hehenian.com',
			'image' : 'http://static.hehenian.com'
		},
		path : [ {
			reg : '**.js',
			release : '$&'
		}, {
			reg : '**.css',
			release : '$&'
		} ]
	},
	project : {
		exclude : 'src/**'
	}
});