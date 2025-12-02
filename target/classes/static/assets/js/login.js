$(document).ready(function () {

});

async function logearUsuario (){
    let datos={};

    datos.email=document.getElementById('txtEmail').value;
    datos.password=document.getElementById('txtPassword').value;

    const REQUEST = await fetch('login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    const RESPONSE = await REQUEST.text();
    if(RESPONSE != 'FAIL'){
        localStorage.token = RESPONSE;
        localStorage.email= datos.email;
        window.location.href = 'all-staff.html'
    }else{
        alert("USUARIO O CONTRASENA INCORRECTA")
    }
}

