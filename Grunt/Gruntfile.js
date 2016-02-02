module.exports = function (grunt) {

  // Autoload grunt tasks
  require('load-grunt-tasks')(grunt);

  var appConfig = {
    app: require('./bower.json').appPath || 'app',
    dist: 'dist',
    tmp: '.tmp',
    server: 'server'
  };

  grunt.initConfig({

    config: appConfig,

    // Cleanup directories
    clean: {
      server: '<%= config.server %>'
    },

    // Copy source files into another directory
    copy: {
      server: { // subtask for dev
        files: [{
          expand: true,
          cwd: '<%= config.app %>',
          src: ['**', '!**/*.less'],
          dest: '<%= config.server %>/'
        }]
      }
    },

    // Include bower dependencies into index.html
    wiredep: {
      server: {
        src: ['<%= config.server %>/index.html'],
        ignorePath: /\.\.\//
      }
    },

    // Watching project source files
    watch: {
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= config.server %>/**'
        ]
      }
    },

    // Start grunt server
    //connect: {
    //  options: {
    //    port: 9000,
    //    hostname: 'localhost',
    //    livereload: 35729,
    //    open: false
    //  },
    //  livereload: {
    //    options: {
    //      open: false,
    //      middleware: function (connect) {
    //        return [
    //          connect.static(appConfig.server), // Use files from tmp directory to run
    //          connect().use('/bower_components', connect.static('./bower_components'))
    //        ];
    //      }
    //    }
    //  }
    //}

    // Start server
    connect: {
      options: {
        port: 9000,
        //base: '<%= config.server %>',
        hostname: 'localhost',
        livereload: 35729
      },

      livereload: {
        open: true,
        middleware: function (connect) {
          return [
            connect.static(appConfig.server),
            connect().use('/bower_components', connect.static('./bower_components'))
          ];
        }
      }
    }

  });

  /* Run server

   - clean server directory
   - copy sources into server directory
   the remaining tasks use the files from the server directory
   - include bower dependencies
   - start grunt server
   - start watching project source files
   */
  grunt.registerTask('server', [
    'clean:server',
    'copy:server',
    'wiredep:server',
    'connect',
    'watch'
  ]);

};
