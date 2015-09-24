//Appelle le Toaster
function showToast(toast) {
    Android.showToast(toast);
}

//Ajoute un élément à la grille
function addGridElem(row, col, el){
    //Notification au controler Android
    if(Android.addGridElem(row, col, el)){
        $("#row-" + row + " .col-" + col + ":first")
        .empty()
        .html(
            el == "circle"
            ? $("<img src='" + window.basePath + "drawable/circle.png" + "'/>")
            : $("<img src='" + window.basePath + "drawable/cross.png" + "'/>")
        );
    }
}

function triggerCleanup(){
    $("#grid tr td").each(function(){
        $(this).empty();
    });
}

$("document").ready(function(){
    //Récupération de l'objet "player" pour les binds
    var player_element = Android.getPlayerElement();
    //Bind des events sur le tableau (pour le player uniquement : l'ordi a pas besoin de cliquer !)
    $("#grid tr td").each(function(){
        $(this).click(function(){
            if(Android.canIPlay()){
                var col = $(this).attr("class").split("-")[1];
                var row = $(this).parent().attr("id").split("-")[1];
                showToast("Clicked(" + row + ", " + col + ")");
                addGridElem(Number(row), Number(col), player_element);
                Android.doComputerTurn();
            };
        });
    });
});