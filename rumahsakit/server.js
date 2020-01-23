const server = require('express')()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const port = 5000
const cors = require('cors')

const mongoURI = 'mongodb://127.0.0.1/dbrumahsakit'

server.use(cors())

mongoose.connect(mongoURI, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('Connect to db succes')
}).catch(err =>{
    console.log('Error : '+ err)
})

server.use(bodyParser.json({
    extended: true,
    limit:'50mb'
}))

server.use(bodyParser.urlencoded({
    extended: true,
    limit:'50mb'
}))

//list router

server.use('/register', require('./Routers/register'))

server.use('/rumahsakit', require('./Routers/rumahsakit'))

server.listen(port, function() {
    console.log('Server started on port'+port)
})

