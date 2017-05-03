var DIRECTOR_URL = "/director";
var DIRECTORS_URL = "/directors";
// var URL = "http://localhost:8180";
var URL = "http://localhost:8088";

var oldValues = [];


$.dto = null;

getAllDirectors();

function getAllDirectors() {
    console.log('getAllDirectors');
    $.ajax({
        type: 'GET',
        url: URL+DIRECTORS_URL,
        dataType: "json", // data type of response
        success: renderDirectorsList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getAllDirectors: ' + textStatus);
        }
    });
}

function renderDirectorsList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#directorList tr').remove();
    $.each(dto, function (index, director) {
        drawRow(director);
    });
}


function drawRow(director) {
    var str = "row"+director.directorId;
    var row = $("<tr id='"+str+"'/>")
    row.append($("<td hidden id='directorId_"+str+"'>" +  director.directorId + "</td>"));
    row.append($("<td id='firstName_"+str+"'>" +  director.firstName + "</td>"));
    row.append($("<td id='lastName_"+str+"'>" + director.lastName + "</td>"));
    row.append($("<td id='averageRating_"+str+"'>" + director.averageRating.toFixed(1) + "</td>"));
    row.append($( "<td id='buttonGroup_"+str+"'>" +
        "<button name='edit' id='editButton_" + str + "' onclick=\"editDirector(" +
        director.directorId +
        ")\">Edit</button>" +
        '<button onclick="deleteDirector(' + director.directorId + ')">Delete</button>' +
        '</td>'));
    $("#directorList").append(row);

}

$('#btnShow').click(function () {
    $("#addDirectorForm").show();
    clearSelect();
});

function clearSelect(directorId) {
    if($(".selected").length==0) return;

    $(".selected").each(function (index, elem) {
        if($(this).val() != oldValues[index]){
            console.log(index+"  :"+$(this).val()+"   :  "+oldValues[index]+"    ");
            if(confirm("The director was changed. Do you want to save him?")){
                updateDirector(directorId);
            }else {
                $(this).val(oldValues[index]);
            }
        }
    });

    $(".selectedRow").attr("style", "");
    $(".selectedRow").attr("class", "");
    $("input.selected").each(function (index, elem) {
        console.log($(this).tagName)
        var val = $(this).val();
        $(this).parent().empty().html(val);
    })

    var s = $("select.selected option:selected").text();
    $("select.selected option:selected").parent().parent().empty().html(s);

    $("button:hidden[name='edit']").show();
    $("button.selectedBtn").remove();

}

$('#btnSave').click(function () {
    addDirector();
    clearAddDirector();
    $("#addDirectorForm").hide();

    return false;
});

function addDirector() {
    console.log('addDirector');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: URL+DIRECTOR_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Director added successfully');
            console.log('Director added successfully');
            getAllDirectors();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addDirector error: ' + errorThrown);
        }
    });
}

function formToJSON() {
    return JSON.stringify({
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
    });
}

function clearAddDirector() {
    $("#firstName").val("");
    $("#lastName").val("");
}

$('#btnClean').click(function () {
    clearAddDirector();
});

function editDirector(directorId) {

    clearSelect(directorId);
    $("#addMovieForm").hide();


    var row = "row"+directorId;
    var val=[];
    var str="";
    $("#"+row).css("border", "2px solid red");
    $("#"+row).attr("class", "selectedRow");

    var s = $("#directorId_"+row).text();
    oldValues[0] = s;
    $("#directorId_"+row).text("")
    $("#directorId_"+row)
        .append("<input class='selected' id='directorId_forUpdate' name='"+row+"' type='text' value='"+s+"' >");

    var s = $("#firstName_"+row).text();
    oldValues[1] = s;
    s = s.replace("'", "&quot;")
    $("#firstName_"+row).text("")
    $("#firstName_"+row)
        .append("<input class='selected' id='firstName_forUpdate' name='"+row+"' type='text' value='"+s+"' size='10'>");

    s = $("#lastName_"+row).text();
    oldValues[2] = s;
    s = s.replace("'", "&quot;")
    $("#lastName_"+row).text("")
    $("#lastName_"+row)
        .append("<input class='selected' id='lastName_forUpdate' name='"+row+"' type='text' value='"+s+"' size='10'>");


    $("#editButton_"+row).hide();
    $("#buttonGroup_"+row);
    $("#buttonGroup_"+row).prepend("<button class='selectedBtn' id='btnUpdateDirector' onclick=\"updateDirector(" +
        directorId +
        ")\">Save</button>");

}


function updateDirector() {
    console.log('updateDirector');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: URL+DIRECTOR_URL,
        data: formToJSON_forUpdate(),
        success: function (data, textStatus, jqXHR) {
            alert('Director updated successfully');
            console.log('Director updated successfully');
            getAllDirectors();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateDirector error: ' + errorThrown);
        }
    });
}

function formToJSON_forUpdate() {
    return JSON.stringify({
        "directorId": $('#directorId_forUpdate').val(),
        "firstName": $('#firstName_forUpdate').val(),
        "lastName": $('#lastName_forUpdate').val(),
    });
}


function deleteDirector(directorId) {
    clearSelect(directorId);
    if (confirm("Delete this director: " + directorId + "?")) {
        var url = URL + DIRECTOR_URL +"/"+ directorId;

        $.ajax({
            type: "DELETE",
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert('Director deleted successfully');
                getAllDirectors();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('delete error: ' + textStatus + directorId);
            }
        })
    }

}