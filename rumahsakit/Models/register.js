const mongoose = require('mongoose')
const Schema = mongoose.Schema
registerSchema = new Schema({
  User: {
    type: String
  },
  Notelpon: {
    type: String
  },
  Password: {
    type: String
  }
})

module.exports = mongoose.model('register', registerSchema)