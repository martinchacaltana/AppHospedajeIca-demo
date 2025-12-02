$(document).ready(function () {
    cargarUsuarios();
    $('#usuarios').DataTable();
    actualizaremail();
});
function actualizaremail(){
    document.getElementById('txtemail-usuario').outerHTML = localStorage.email;
}
async function cargarUsuarios (){
        const REQUEST = await fetch('usuarios', {
            method: 'GET',
            headers: getHeaders()
        });
        const USUARIOS = await REQUEST.json();
        let listadoHtml= '';
        for (let usuario of USUARIOS){
            let botonEliminar='<a class="dropdown-item" href="#" onclick="eliminarUsuario('+usuario.id+')" data-toggle="modal" data-target="#delete_asset"><i class="fas fa-trash-alt m-r-5"></i> Borrar</a>';
            let telefonotexto= usuario.telefono == null ? '-' : usuario.telefono;
            let usuarioHtml = '<tr><td><h2 class="table-avatar"> <a href="profile.html" class="avatar avatar-sm mr-2"><img class="avatar-img rounded-circle" src="assets/img/logo.jpg" alt="User Image"></a><a href="profile.html">'+usuario.nombre+' '+usuario.apellido+'<span>'+usuario.id+'</span></a></h2> </td><td>ST-006</td><td><a href="/cdn-cgi/l/email-protection" class="__cf_email__" data-cfemail="badedbccd3dedbd6ccdbc8dfc0faddd7dbd3d694d9d5d7">[email&#160;protected]</a></td> <td>'+telefonotexto+'</td> <td>22-04-2020</td> <td>07.00 PM</td> <td>Yes</td> <td> <div class="actions"> <a href="#" class="btn btn-sm bg-success-light mr-2">Trabajador</a> </div> </td> <td> <div class="actions"> <a href="#" class="btn btn-sm bg-success-light mr-2">Activo</a> </div> </td> <td class="text-right"> <div class="dropdown dropdown-action"> <a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fas fa-ellipsis-v ellipse_color"></i></a> <div class="dropdown-menu dropdown-menu-right"> <a class="dropdown-item" href="edit-staff.html"><iclass="fas fa-pencil-alt m-r-5"></i> Editar</a>'+botonEliminar+'</div></div></td></tr> '
            listadoHtml+=usuarioHtml;
        }

        console.log(USUARIOS);
        document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}
function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}
async function eliminarUsuario(id){
    if(!confirm('Desea eliminar el usuario?')){
        return;
    }
    const REQUEST = await fetch('usuario/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload()
}