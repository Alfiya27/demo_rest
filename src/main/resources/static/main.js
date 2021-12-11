$(async function () {
    await getTableWithUsers();
    await getTableWithAdmin();
    await getDefaultModal();
    await addNewUser();
    await getRolesByUser();
    await getUser();
    await getRoles();

})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('users'),
    findOneUser: async (id) => await fetch(`${id}`),
    addNewUser: async (user) => await fetch('create', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`${id}`, {method: 'PUT', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`${id}`, {method: 'DELETE', headers: userFetchService.head}),
    findRoles: async () => await fetch('roles'),
    findIdent: async () => await fetch('user')
}
async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    table.empty();
    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.lastName}</td>                        
                        <td>${user.email}</td>                        
                        <td> ${user.roles.map(role => "  " + role.name)}</td>
                        <td>
                            <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info" 
                            data-toggle="modal" data-target="#someDefaultModal">Edit</button>
                        </td>
                        <td>
                             <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger" 
                             data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                        </td>
                    </tr>
                )`;
                table.append(tableFilling);
            })
        })
    // обрабатываем нажатие на любую из кнопок edit или delete
    // достаем из нее данные и отдаем модалке, которую к тому же открываем
    $("#mainTableWithUsers").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}
async function getTableWithAdmin() {
    let table = $('#mainTableWithAdmin tbody');
    await userFetchService.findIdent()
        .then(res => res.json())
        .then(user => {
            let tableFilling = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>                
                <td>${user.email}</td>          
                <td> ${user.roles.map(role => "  " + role.name)}</td>
            </tr>
            )`;
            table.append(tableFilling);
        })
}

async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}
async function editUser(modal, id) {
    let preuser = await userFetchService.findOneUser(`${id}`);
    let user = preuser.json();
    let editRoles = [];
    await userFetchService.findRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                editRoles.push(role);
            })
        })
    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-outline-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
                <label for="id" class="font-weight-bold">ID
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                <label th:for="login" class="font-weight-bold">Login
                <input class="form-control" type="text" id="login" value="${user.login}">
                <label th:for="name" class="font-weight-bold">First Name
                <input class="form-control" type="text" id="name" value="${user.name}">
                <label th:for="lastName" class="font-weight-bold">Last Name
                <input class="form-control" type="text" id="lastName" value="${user.lastName}">              
                <label th:for="email" class="font-weight-bold">Email
                <input class="form-control" id="email" type="text" value="${user.email}">
                <label th:for="password" class="font-weight-bold">Password
                <input class="form-control" type="password" id="password">
                
                <label th:for="password" class="font-weight-bold">Role<br>
                <select class="form-control" id="mySelectId" name="mySelect" multiple size="2">
                    <option value="${editRoles[0].name}">${editRoles[0].name}</option>
                    <option value="${editRoles[1].name}">${editRoles[1].name}</option>
                </select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    });

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let login = modal.find("#login").val().trim();
        let name = modal.find("#name").val().trim();
        let lastName = modal.find("#lastName").val().trim();
        let email = modal.find("#email").val().trim();
        let password = modal.find("#password").val().trim();
        let editRole = [];

        let elementEditRole = document.getElementById("mySelectId");
        for (let i = 0; i < elementEditRole.options.length; i++) {
            let singleRole = elementEditRole.options[i];
            if (singleRole.selected) editRole.push(singleRole.value);
        }

        let data = {
            id: id,
            login: login,
            name: name,
            lastName: lastName,
            email: email,
            password: password,
            roles: editRole
        }
        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            getTableWithUsers();
            modal.modal('hide');
        }else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}
async function deleteUser(modal, id) {
    let preuser = await userFetchService.findOneUser(`${id}`);
    let user = preuser.json();
    let deleteRoles = [];
    await userFetchService.findRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                deleteRoles.push(role);
            })
        })
    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="deleteUser">
                <label for="id" class="font-weight-bold">ID
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <label th:for="login" class="font-weight-bold">Login
                <input class="form-control" type="text" id="login" value="${user.login}" disabled><br>
                <label th:for="name" class="font-weight-bold">First Name
                <input class="form-control" type="text" id="name" value="${user.name}" disabled><br>
                <label th:for="lastName" class="font-weight-bold">Last Name
                <input class="form-control" type="text" id="lastName" value="${user.lastName}" disabled><br>                
                <label th:for="email" class="font-weight-bold">Email
                <input class="form-control" type="text" id="email" value="${user.email}" disabled><br>               
                <label th:for="roles" class="font-weight-bold">Role<br>
                <select class="form-control" id="mySelectId" name="mySelectDelete" multiple size="2">
                    <option value="${deleteRoles[0].name}" disabled>${deleteRoles[0].name}</option>
                    <option value="${deleteRoles[1].name}" disabled>${deleteRoles[1].name}</option>
                </select>            
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })
    $("#deleteButton").on('click', async () => {
        const response = await userFetchService.deleteUser(id);
        getTableWithUsers();
        modal.modal('hide');
    })
}

async function addNewUser() {
    let addRoles = [];
    await userFetchService.findRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                addRoles.push(role);
            })
        })
    let select = $('#defaultSomeForm div');
    let bodyFilling = `
        <select class="form-control" id="mySelectForAddId" name="mySelect" multiple size="2">
            <option value="${addRoles[0].name}">${addRoles[0].name}</option>
            <option value="${addRoles[1].name}">${addRoles[1].name}</option>
        </select>
    `;
    select.append(bodyFilling);
    $('#addNewUserButton').click(async () => {
        let addUserForm = $('#defaultSomeForm')
        let login = addUserForm.find('#AddNewUserLogin').val().trim();
        let name = addUserForm.find('#AddNewUserName').val().trim();
        let lastName = addUserForm.find('#AddNewUserLastName').val().trim();
        let email = addUserForm.find('#AddNewUserEmail').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let roleByUserAdd = [];

        let elementRole = document.getElementById("mySelectForAddId");
        for (let i = 0; i < elementRole.options.length; i++) {
            let singleRole = elementRole.options[i];
            if (singleRole.selected) roleByUserAdd.push(singleRole.value);
        }

        let data = {
            login: login,
            name: name,
            lastName: lastName,
            email: email,
            password: password,
            roles: roleByUserAdd
        }
        const response = await userFetchService.addNewUser(data);
        if (response.ok) {
            await getTableWithUsers();
            addUserForm.find('#AddNewUserLogin').val('');
            addUserForm.find('#AddNewUserName').val('');
            addUserForm.find('#AddNewUserLastName').val('');
            addUserForm.find('#AddNewUserEmail').val('');
            addUserForm.find('#AddNewUserPassword').val('');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })
}
async function getRoles() {
    let allRoles = [];
    await userFetchService.findRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                allRoles.push(role);
            })
        })
    return allRoles;
}

async function getUser() {
    let userFind = $('#getUser');
    await userFetchService.findIdent()
        .then(res => res.json())
        .then(user => {
            let thisUser = user.login
            userFind.append(thisUser);
        })
}

async function getRolesByUser() {
    let roleFind = $('#RolesByUser');
    await userFetchService.findIdent()
        .then(res => res.json())
        .then(user => {
            let rolesUser = user.roles.map(role => "  " + role.name)
            roleFind.append(rolesUser);
        })
}


