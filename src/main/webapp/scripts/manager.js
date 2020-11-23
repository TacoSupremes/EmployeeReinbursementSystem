handleRequest = (name, id) => {

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let rowRemove = document.getElementById(`row${id}`)

            rowRemove.remove()
        }

    }
        xhr.open('POST', `http://localhost:8080/ExpenseReimbursementSystem/api/${name}Request`) //readyState 1
        xhr.send(id) //readyState 2
}


getRequests = () => {


    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let requests = JSON.parse(xhr.response)

            let table = document.querySelector('.table1')

            requests.forEach( request => {
                let tr = document.createElement('tr')
                tr.id = `row${request.requestID}`
               

                let name = document.createElement('td')
                name.textContent = request.employeeName

                let amount = document.createElement('td')
                amount.textContent = request.amount

                let reason = document.createElement('td')
                reason.textContent = request.reason

                let created = document.createElement('td')
                created.textContent = request.created

                let reviewed = document.createElement('td')

                if(request.reviewed == undefined)
                {
                    reviewed.textContent = "Not Reviewed"
                }
                else
                {
                    reviewed.textContent = request.reviewed
                }

                let picture = document.createElement('td')

                let link = document.createElement('a')
                link.href = "/ExpenseReimbursementSystem/api/recieptPhotos/"+request.imageURI
                link.textContent = "Reciept"
                link.target = "_blank"
                picture.appendChild(link)

                let choice = document.createElement('td')

                let check = document.createElement('img')
                let x = document.createElement('img')

                check.src='../images/green-check.png'
                x.src = '../images/red-x.png'

                check.width = 64
                x.width = 64

                let checkb = document.createElement('button')
                let xb = document.createElement('button')

                checkb.appendChild(check)
                xb.appendChild(x)

                checkb.addEventListener('click', () => handleRequest('approve', request.requestID))
                checkb.style = "margin-right: 5px; margin-left: 10px;"

                xb.addEventListener('click', () => handleRequest('deny', request.requestID))
                xb.style = "margin-left: 5px; margin-right: 10px;"
                choice.appendChild(checkb)
                choice.appendChild(xb)

                choice.style = "display: table; flex-direction: row; margin: 2.5px; border: 0px;"


                tr.appendChild(name)
                tr.appendChild(amount)
                tr.appendChild(reason)
                tr.appendChild(created)
                tr.appendChild(reviewed)
                tr.appendChild(picture)
                tr.appendChild(choice)

                table.appendChild(tr)
            });
         
        }
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursementSystem/api/managerRequests') //readyState 1
    xhr.send() //readyState 2
}

getAllRequests = () => {

    //I want to place cards inside of this div. BUT I don't have any; I haven't set up a data source which contains representations of cards, but that's okay because someone else already has. There is (somewhere) a resource available over the network which will provide clients with representations of yugiyo cards (e.g. as JSON). We'll use that resource to grab our cards.

    //Let's use AJAX to accomplish this task!
    //Let's start by creating an XMLHttpRequest object:

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let requests = JSON.parse(xhr.response)

            let table = document.querySelector('.table2')

            requests.forEach( request => {
                let tr = document.createElement('tr')
                let status = document.createElement('td')

                if(request.pending == undefined)
                {
                    status.textContent = 'Rejected'
                }
                else if (request.pending == true)
                {
                    status.textContent = 'Pending'
                }
                else
                {
                    status.textContent = 'Approved'
                }

                let madeBy = document.createElement('td')
                madeBy.textContent = request.employeeName


                let amount = document.createElement('td')
                amount.textContent = request.amount

                let reason = document.createElement('td')
                reason.textContent = request.reason

                let created = document.createElement('td')
                created.textContent = request.created

                let reviewed = document.createElement('td')

                if(request.reviewed == undefined)
                {
                    reviewed.textContent = "Not Reviewed"
                }
                else
                {
                    reviewed.textContent = request.reviewed
                }

                let picture = document.createElement('td')

                let link = document.createElement('a')
                link.href = "/ExpenseReimbursementSystem/api/recieptPhotos/"+request.imageURI
                link.textContent = "Reciept"
                link.target = "_blank"
                picture.appendChild(link)


                let assignedTo = document.createElement('td')
                assignedTo.textContent = request.managerName


                tr.appendChild(status)
                tr.appendChild(madeBy)
                tr.appendChild(amount)
                tr.appendChild(reason)
                tr.appendChild(created)
                tr.appendChild(reviewed)
                tr.appendChild(picture)
                tr.appendChild(assignedTo)
                table.appendChild(tr)
            });
         
        }
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursementSystem/api/allRequests') //readyState 1
    xhr.send() //readyState 2
}


getAllUsers = () => {

    //I want to place cards inside of this div. BUT I don't have any; I haven't set up a data source which contains representations of cards, but that's okay because someone else already has. There is (somewhere) a resource available over the network which will provide clients with representations of yugiyo cards (e.g. as JSON). We'll use that resource to grab our cards.

    //Let's use AJAX to accomplish this task!
    //Let's start by creating an XMLHttpRequest object:

    let xhr = new XMLHttpRequest() //readyState 0

    xhr.onreadystatechange = () => {
        //You decide what to do each time the readyState changes in this function! Be sure to check that the readyState is 4 and that the response code is 200 (meaning everything went smoothly!)

        if(xhr.readyState === 4 & xhr.status === 200)
        {
            let requests = JSON.parse(xhr.response)

            let table = document.querySelector('.table3')

            requests.forEach( request => {
                let tr = document.createElement('tr')
              
                let name = document.createElement('td')
                name.textContent = request.employeeName


                let email = document.createElement('td')
                email.textContent = request.email

                let manager = document.createElement('td')
                manager.textContent = request.manager

                let managedBy = document.createElement('td')
                managedBy.textContent = request.managerName


                tr.appendChild(name)
                tr.appendChild(email)
                tr.appendChild(manager)
                tr.appendChild(managedBy)
                table.appendChild(tr)
            });
         
        }
    }
    
    xhr.open('GET', 'http://localhost:8080/ExpenseReimbursementSystem/api/allUsers') //readyState 1
    xhr.send() //readyState 2
}


// The modal code is from the example on https://www.w3schools.com/howto/howto_css_modals.asp
var modal = document.getElementById("myModal")

// Get the button that opens the modal
var btn = document.getElementById("myBtn")

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0]

// When the user clicks on the button, open the modal
btn.onclick = function() {
  modal.style.display = "block"
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none"
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none"
  }
}

window.onload = () => {
    getRequests()
    getAllRequests()
    getAllUsers()
}

