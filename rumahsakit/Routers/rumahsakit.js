const rumahsakit = require('express').Router()
const rumahsakitController = require('../Controller/rumahsakit')

rumahsakit.post('/insert', (req, res) => {
    rumahsakitController.insertrumahsakit(req.body)
        .then(result => res.json(result))
        .catch(error => res.json(error))
})


// rumahsakit.post('/login', (req, res) => {
//     rumahsakitController.login(req.body)
//     .then(result => {
//         res.json(result)
//     }).catch(err => {
//         res.json(err)
//     })
// })

rumahsakit.get('/getAllrumahsakit', (req, res) => {
    rumahsakitController.getAllrumahsakit()
    .then(result => res.json(result))
    .catch(error => res.json(error))
})

module.exports = rumahsakit