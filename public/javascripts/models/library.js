/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/12/13
 * Time: 11:20 PM
 */

var app = app || {};

app.Library = Backbone.Collection.extend({
  model: app.Book
});
