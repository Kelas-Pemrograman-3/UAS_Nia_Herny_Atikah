const rumahsakitModel = require('../models/rumahsakit')
	
	exports.insertrumahsakit = (data) =>
	  new Promise((resolve, reject) => {
	    rumahsakitModel.create(data)
	      .then(res => {
	        resolve({
	          error : false,
	          pesan: 'Berhasil Input Data'
	        })
	      })
	      .catch(() => {
	        reject({
	          error: true,
	          pesan: 'Gagal Input Data'
	        })
	      })
	  })

	
	exports.getAllrumahsakit = () =>
new Promise((resolve, reject) => {
    rumahsakitModel.find()
        .then(result => {
            resolve({
                error: false,
                pesan: 'Berhasil Mengambil Data',
                data : result
            })
        }).catch(() => {
            reject({
                error: true,
                pesan: 'Gagal Mengambil Data'
        })
 })
    })