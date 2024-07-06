const tokenFipe = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJmYjE3OGQ4OS00Y2IwLTRjY2EtYWI0ZC1mMDE4YWU0NDJhNjAiLCJlbWFpbCI6ImVkc29ubHVpei56dWNoaUBnbWFpbC5jb20iLCJpYXQiOjE3MjAyOTEwMDR9.WS2VvSd1CfZbNH3ZHOt970jdfx_yCoh4N_vBmeKDQ0w'

function findByBrand(type){
    $('#type_vehicle').find('option').remove()

    $.ajax({
        url: "https://fipe.parallelum.com.br/api/v2/"+type+"/brands",
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": tokenFipe
        },
        success: function(data){
            try {
                data.forEach(element => {
                    $('#type_vehicle').append('<option value='+element.code+'>'+element.name+'</option>');
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