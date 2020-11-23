let first_name = document.querySelector('#firstname');
let last_name = document.querySelector('#lastname');
let email = document.querySelector('#email');
let password = document.querySelector('#password');

//let save = document.querySelector('#save');

let form = document.querySelector('#userform');

form.addEventListener('submit', (event)=>{

    event.preventDefault();

    let changes = document.querySelector('#changes')

    if(changes)
    {
      changes.remove()
    }

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
                let n = document.createElement('h4');
                n.id='changes'
                n.innerText="Changes were successfully saved";
                form.append(n);
        }

        console.log(xhr.readyState)
    }
    
    auth = {
        account_num: -1,
        last_name: last_name.value,
        first_name: first_name.value,
        email: email.value,
        pass: password.value
    }
    console.log(auth)

    xhr.open('POST', 'http://localhost:8080/ExpenseReimbursementSystem/api/updateUserInfo') //readyState 1
    xhr.send(JSON.stringify(auth)) //readyState 2
})

getUser = () => {

    //Let's use AJAX to accomplish this task!
    //Let's start by creating an XMLHttpRequest object:

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let user = JSON.parse(xhr.response)

                first_name.value = user.first_name
                last_name.value = user.last_name
                email.value = user.email
                password.value = user.password
        }

     //   console.log(xhr.readyState)
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursementSystem/api/userInfo') //readyState 1
    xhr.send() //readyState 2
}

window.onload = () => {
    getUser()
}