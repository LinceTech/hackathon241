const tokenFipe = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJmYjE3OGQ4OS00Y2IwLTRjY2EtYWI0ZC1mMDE4YWU0NDJhNjAiLCJlbWFpbCI6ImVkc29ubHVpei56dWNoaUBnbWFpbC5jb20iLCJpYXQiOjE3MjAyOTEwMDR9.WS2VvSd1CfZbNH3ZHOt970jdfx_yCoh4N_vBmeKDQ0w'

function findByType(type){
    $('#brand').find('option').remove()

    $.ajax({
        url: "https://fipe.parallelum.com.br/api/v2/"+type+"/brands",
        method: "GET",
        dataType: "json",
        headers: {
            "X-Subscription-Token": tokenFipe
        },
        success: function(data){
            try {
                data.forEach(element => {
                    $('#brand').append('<option value='+element.code+'>'+element.name+'</option>');
                })
            }catch (e){
                console.log(e)
            }
        },
        error: function(data){
            console.log(data)
        }
    });
}

function findByBrand(type, brand){
    $('#model').find('option').remove()

    $.ajax({
        url: "https://fipe.parallelum.com.br/api/v2/"+type+"/brands/"+brand+"/models",
        method: "GET",
        dataType: "json",
        headers: {
            "X-Subscription-Token": tokenFipe
        },
        success: function(data){
            try {
                data.forEach(element => {
                    $('#model').append('<option value='+element.code+'>'+element.name+'</option>');
                })
            }catch (e){
                console.log(e)
            }
        },
        error: function(data){
            console.log(data)
        }
    });
}

function findByBrandAndModel(type, brand){
    $('#model').find('option').remove()

    $.ajax({
        url: "https://fipe.parallelum.com.br/api/v2/"+type+"/brands/"+brand+"/models/years",
        method: "GET",
        dataType: "json",
        headers: {
            "X-Subscription-Token": tokenFipe
        },
        success: function(data){
            try {
                $.ajax({
                    url: "https://fipe.parallelum.com.br/api/v2/"+type+"/brands/"+brand+"/models/years/"+data[0].code,
                    method: "GET",
                    dataType: "json",
                    headers: {
                        "X-Subscription-Token": tokenFipe
                    },
                    success: function(data){

                    },
                    error: function(data){
                        console.log(data);
                    }
                })
            }catch (e){
                console.log(e)
            }
        },
        error: function(data){
            console.log(data)
        }
    });
}
