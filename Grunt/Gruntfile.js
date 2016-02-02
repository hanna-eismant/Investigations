module.exports = function (grunt) {

  // Autoload grunt tasks
  require('load-grunt-tasks')(grunt);
  var serveStatic = require('serve-static');

  var appConfig = {
    app: require('./bower.json').appPath || 'app',
    dist: 'dist',
    tmp: 'tmp',
    server: 'server',
    bower: 'bower_components'
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

    // Start server
    connect: {
      options: {
        port: 9000,
        hostname: 'localhost',
        livereload: 35729,
        middleware: function (connect) {
          return [
            serveStatic(appConfig.server),
            connect().use('/bower_components', serveStatic(appConfig.bower))
          ];
        }
      },

      livereload: {
        options: {
          open: false
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
