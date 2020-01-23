const mongoose = require('mongoose')
const Schema = mongoose.Schema

const rumahsakitSchema = new Schema({
namarumahsakit: {
    type: String,
    indexes: {
        unique: true
    }
},
alamat: {
    type: String
},
Notelpon: {
  type: String
},
Lokasi: [
  {
   lat: String,
   long: String
  }
]
})


module.exports = mongoose.model('rumahsakit', rumahsakitSchema)