$(async function () {
    await getTableWithOneUser();
    await getRolesByUser();
    await getUser();
})

async function getUser() {
    let userFind = $('#getUser b');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let getUser = user.login
            userFind.append(getUser);
        })
}

async function getRolesByUser() {
    let roleFind = $('#RolesByUser');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let rolesUser = user.roles.map(role => "  " + role.name)
            roleFind.append(rolesUser);
        })
}

async function getTableWithOneUser() {
    let tableUser = $('#tabUser tbody');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let tableFillingUser = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>               
                <td>${user.email}</td>                
                <td> ${user.roles.map(role => "  " + role.name)}</td>
            </tr>
            )`;
            tableUser.append(tableFillingUser);
        })
}
