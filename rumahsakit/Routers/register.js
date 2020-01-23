const register = require('express')()
const registerController = require('../controller/register')


register.post('/insert', (req, res) => {
  registerController.insertuser(req.body)
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

register.post('/login', (req, res)=> {
  registerController.login(req.body)
  .then(result => {
  res.json(result)
  }).catch(err => {
    res.json(err)
  })
})





module.exports = register