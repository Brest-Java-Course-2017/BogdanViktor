var MOVIES_URL = "/movies";
var MOVIE_URL = "/movie";
var DIRECTORS_URL = "/directors";
var URL = "http://localhost:8180";
var FILTER_URL="/movies/DateFilter";

$.dto = null;


// Register listeners
$('#btnSave').click(function () {
    addMovie();
    clearAddMovie();
    $("#addMovieForm").hide();

    return false;
});

$('#btnFilter').click(function () {
    var url = URL + FILTER_URL;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        data: {
            "startDate": $("#startDate").val(),
            "endDate": $("#endDate").val(),
        },
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('filter movie: ' + textStatus);
        }
    });
});

$('#btnClean').click(function () {
    clearAddMovie();
});

function clearAddMovie() {
    $("#movieTitle").val("");
    $("#releaseDate").val("");
    $("#rating").val("");
}

$('#btnShow').click(function () {
    $("#addMovieForm").show();
    clearSelect();
    getAllDirectors();
});

function addMovie() {
    console.log('addMovie');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: URL+MOVIE_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Movie added successfully');
            console.log('Movie added successfully');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addMovie error: ' + errorThrown);
        }
    });
}


function formToJSON() {
    return JSON.stringify({
        "movieId":  null,
        "movieTitle": $('#movieTitle').val(),
        "releaseDate": $('#releaseDate').val(),
        "rating": $('#rating').val(),
        "movieDirectorId": $('#movieDirectorId').val(),
    });
}

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
    $('#movieDirectorId option').remove();
    $.each(dto, function (index, director) {
        var str = "<option value="+director.directorId+">"+director.firstName+" "+director.lastName+"</option>";

        $("#movieDirectorId").append(str);
    });
}

$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedUserId = $(this).data("id");
    if (action != "delete") {
        $.each(dto, function (index) {
            if (dto[index].userId == selectedUserId) {
                $("#userId").val(selectedUserId);
                $("#login").val(dto[index].login);
                $("#password").val(dto[index].password);
                $("#description").val(dto[index].description);
            }
        });
    } else {
        deleteUser(selectedUserId);
    }
});


findAll();

function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: URL+MOVIES_URL,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
}



function deleteMovie(movieId, movieTitle) {
    if (confirm("Delete this movie: " + movieTitle + "?")) {
        var url = URL + MOVIE_URL +"/"+ movieId;

        $.ajax({
            type: "DELETE",
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert('Movie deleted successfully');
                findAll();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('delete error: ' + textStatus + movieTitle);
            }
        })
    }
}

function renderList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#movieList tr').remove();
    $.each(dto, function (index, movie) {
        drawRow(movie);
    });
}

function drawRow(movie) {
    var str = "row"+movie.movieId;
    var row = $("<tr id='"+str+"'/>")
    row.append($("<td hidden id='movieId_"+str+"'>" +  movie.movieId + "</td>"));
    row.append($("<td id='movieTitle_"+str+"'>" +  movie.movieTitle + "</td>"));
    row.append($("<td id='releaseDate_"+str+"'>" + movie.releaseDate + "</td>"));
    row.append($("<td id='rating_"+str+"'>" + movie.rating.toFixed(1) + "</td>"));
    row.append($("<td id='director_"+str+"'>" + movie.directorsFirstName+" "+movie.directorsLastName+ "</td>"));
    row.append($( "<td id='buttonGroup_"+str+"'>" +
        "<button name='edit' id='editButton_" + str + "' onclick=\"editMovie(" +
         movie.movieId +
        ")\">Edit</button>" +
        '<button onclick="deleteMovie(' + movie.movieId +","+"'"+movie.movieTitle+"'"+ ')">Delete</button>' +
        '</td>'));
    $("#movieList").append(row);

}



function editMovie(movieId) {
clearSelect();
    $("#addMovieForm").hide();


    var row = "row"+movieId;
    var val=[];
    var str="";
    $("#"+row).css("border", "2px solid red");
    $("#"+row).attr("class", "selectedRow");
    // $("#"+row).wrap("<form></form>");

    var s = $("#movieId_"+row).text();
    $("#movieId_"+row).text("")
    $("#movieId_"+row)
        .append("<input class='selected' id='movieId_forUpdate' name='"+row+"' type='text' value='"+s+"' >");

    var s = $("#movieTitle_"+row).text();
    $("#movieTitle_"+row).text("")
    $("#movieTitle_"+row)
        .append("<input class='selected' id='movieTitle_forUpdate' name='"+row+"' type='text' value='"+s+"' size='40'>");

    s = $("#releaseDate_"+row).text();
    $("#releaseDate_"+row).text("")
    $("#releaseDate_"+row)
        .append("<input class='selected' id='releaseDate_forUpdate' name='"+row+"' type='text' value='"+s+"' size='10'>");

    s = $("#rating_"+row).text();
    $("#rating_"+row).text("")
    $("#rating_"+row)
        .append("<input class='selected' id='rating_forUpdate'  name='"+row+"' type='text' value='"+s+"' size='3'>");

    s = $("#director_"+row).text();
    $("#director_"+row).text("")
    $("#director_"+row)
        .append("<select name='"+s+"' id='movieDirectorId_forUpdate'  class='selected' style='width: 70%'> </select>");


    getAllDirectorsForEdit();

    $("#editButton_"+row).hide();
    $("#buttonGroup_"+row);
    $("#buttonGroup_"+row).prepend("<button class='selected' id='btnUpdateMovie' onclick=\"updateMovie(" +
        movieId +
        ")\">Save</button>");

}

function getAllDirectorsForEdit() {
    console.log('getAllDirectorsForEdit');
    $.ajax({
        type: 'GET',
        url: URL+DIRECTORS_URL,
        dataType: "json", // data type of response
        success: renderDirectorsListForEdit,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getAllDirectors: ' + textStatus);
        }
    });
}

function renderDirectorsListForEdit(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    var r = $("select.selected").attr("name");

    $.each(dto, function (index, director) {
        var directorName = director.firstName+" "+director.lastName;
        if(r==directorName){
            var str = "<option selected value="+director.directorId+" >"+director.firstName+" "+director.lastName+"</option>";
            console.log(index+" : " +r + "=" +directorName);

        } else {
            var str = "<option value="+director.directorId+">"+director.firstName+" "+director.lastName+"</option>";
            console.log(index+" : " +r + "=" +directorName);

        }

        $("select.selected").append(str);
    });
}

function clearSelect(event) {
    if($(".selected").length==0) return;

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
    $("button.selected").remove();

}

// Register listeners
$('#btnUpdateMovie').click(function () {
          updateMovie();

    return false;
});

function updateMovie() {
    console.log('updateMovie');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: URL+MOVIE_URL,
        data: formToJSON_forUpdate(),
        success: function (data, textStatus, jqXHR) {
            alert('Movie updated successfully');
            console.log('Movie updated successfully');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateMovie error: ' + errorThrown);
        }
    });
}

function formToJSON_forUpdate() {
     return JSON.stringify({
        "movieId": $('#movieId_forUpdate').val(),
        "movieTitle": $('#movieTitle_forUpdate').val(),
        "releaseDate": $('#releaseDate_forUpdate').val(),
        "rating": $('#rating_forUpdate').val(),
        "movieDirectorId": $('#movieDirectorId_forUpdate').val(),
    });
}