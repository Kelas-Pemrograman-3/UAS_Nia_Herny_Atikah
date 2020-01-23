
const registerModel = require('../models/register')
const bcrypt = require('bcryptjs')

exports.insertuser = (data) =>
  new Promise((resolve, reject) => {
    bcrypt.hash(data.Password, 10,(err, hash)=> {
      data.Password = hash
      registerModel.find({
        User: data.User
      }).then(hasil=> {
        if (hasil.length>0) {
        reject({
          error:true,
          pesan: 'Usern sudah di gunakan'
        })
      } else {
        registerModel.create(data)
        .then(res => {
          resolve({
            error: false,
            pesan: 'Berhasil Input Data'
          })
        })
        .catch(() => {
          reject({
            error: true,
            pesan: 'Username sudah di gunakan'
          })
        })
      }
    })
    })
 
  })

  exports.login = (data) => 
  new Promise((resolve,reject)=> {
    registerModel.findOne({
      User: data.User
    }).then(res => {
     if (res === null) {
       reject({
         error: true,
         pesan: 'UserName Tidak Terdaftar'
       })
     } else {
       let hashPassword = res.Password
      if ( bcrypt.compareSync(data.Password, hashPassword)) {
        resolve({
          error: false,
          pesan: 'Berhasil login',
          data: res
        })
      } else {
        reject({
          error: true,
          pesan: 'Password Anda salah'
        })
      }
     }
    })
  })

  // exports.getAllMahasiswa = () =>
  // new Promise((resolve, reject) => {
  //   MahasiswaModel.find()
  //     .then(res => {
  //       resolve({
  //         error: false,
  //         pesan: 'Berhasil Mengambil Data',
  //         data: res
  //       })
  //     })
  //     .catch(() => {
  //       reject({
  //         error: true,
  //         pesan: 'Gagal Mengambil Data'
  //       })
  //     })
  // })