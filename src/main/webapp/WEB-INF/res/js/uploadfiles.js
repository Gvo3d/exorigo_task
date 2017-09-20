function getName (str){
    if (str.lastIndexOf('\\')){
        var i = str.lastIndexOf('\\')+1;
    }
    else{
        var i = str.lastIndexOf('/')+1;
    }						
    var filename = str.slice(i);			
    var uploadedButton = document.getElementById("uploadSubmit");
    var fileUploadedButton = document.getElementById("uploadedFileName");
    var file = document.getElementById("uploadlabel");

    fileUploadedButton.innerHTML = filename;
    file.className = "btn btn-md btn-default btn-block";

    for (let el of document.querySelectorAll('.dounhide')) el.style.visibility = 'visible';
}