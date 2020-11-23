validatePassword = (event) =>{

    let passWordBox = document.getElementsByTagName('input')
    let pass = passWordBox[1].value;

    if(pass.length < 6)
    {
        // we shpuld show this to the user
        let form = document.getElementById('form')
       
        let message = document.createElement('p')
        message.id = 'warning'
        message.innerText = 'Password must be at least 6 characters long.'
       // message.style.color = 'red'
        if(document.getElementById('warning') == undefined)
       form.append(message)
       
        console.log('not long enough')
        if(event.cancelable)
        {
            event.preventDefault()
        }
    }
    else
    {
        console.log('valid')
        let t = document.getElementById('warning');
        
        if(t != undefined)
        {
            document.removeChild(t)
        }
    }
}


let button = document.querySelector('button')
button.addEventListener('click', validatePassword)