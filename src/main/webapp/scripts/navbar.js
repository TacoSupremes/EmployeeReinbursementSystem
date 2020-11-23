addAdmin = () => {

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let user = JSON.parse(xhr.response)

           // console.log(JSON.stringify(user))

               if(user.manager)
               {
                    let m = document.querySelector('.manager')
                    m.hidden =false;
               }
        }

       // console.log(xhr.readyState)
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursementSystem/api/userInfo') //readyState 1
    xhr.send() //readyState 2
    //<li class="nav-item">
      //                  <a class="nav-link" href="./requests.html">Requests</a>
        //            </li>
}


load = () => {
    addAdmin()
    document.getElementById("logout").onclick = () => {
        document.getElementById("logoutForm").submit();
        return false; // cancel the actual link
      }
}

window.addEventListener("load", load, true)
