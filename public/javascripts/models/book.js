/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/12/13
 * Time: 11:17 PM
 */

var app = app || {};

app.Book = Backbone.Model.extend({
  defaults: {
    coverImage: 'images/eloquent_javascript.png',
    title: 'No title',
    author: 'Unknown',
    releaseDate: 'Unknown',
    keywords: 'None'
  },

  imageUri: function(me) {
    var uri = "@routes.Assets.at(\"" + me.get('coverImage') + "\")";
    console.log(uri);
    return uri;
  }

});
