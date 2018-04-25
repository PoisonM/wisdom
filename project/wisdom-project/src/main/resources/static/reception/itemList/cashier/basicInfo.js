function basicInfo(e, Archives) {
    Archives.get({ userId: '66' }, function(res) {
        if (res.code == 200) {
            console.log('haha')
        }
    })
    console.log(Archives)
}