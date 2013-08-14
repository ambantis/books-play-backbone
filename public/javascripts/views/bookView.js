/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/12/13
 * Time: 11:20 PM
 */

var app = app || {};

app.BookView = Backbone.View.extend({
  tagName: 'div',

  className: 'bookContainer',

  events: {
    'click .delete': 'deleteBook'
  },

  deleteBook: function() {
    this.model.destroy();

    this.remove();
  },

  template: _.template($('#book-template').html()),

  render: function() {
    this.$el.html(this.template(this.model.toJSON()));
    return this;
  }
});
