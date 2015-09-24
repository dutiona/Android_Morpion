//Appelle le Toaster
function showToast(toast) {
    Android.showToast(toast);
}

//Ajoute un élément à la grille
function addGridElem(row, col, el){
    $("#row-" + row + " .col-" + col + ":first")
        .empty()
        .html(el == "circle" ? window.img_circle : window.img_cross);

    Android.addGridElem(row, col, el);
}

$("document").ready(function(){
	//Création des images
    window.img_cross = $("<img src='" + window.basePath + "drawable/cross.png" + "'/>");
    window.img_circle = $("<img src='" + window.basePath + "drawable/circle.png" + "'/>");

    //Récupération de l'objet "player" pour les binds
    var player_element = Android.getPlayerElement();
    //Bind des events sur le tableau (pour le player uniquement : l'ordi a pas besoin de cliquer !)
    $("#grid tr td").each(function(){
        $(this).click(function(){
            var col = $(this).attr("class").split("-")[1];
            var row = $(this).parent().attr("id").split("-")[1];
            showToast("Clicked(" + row + ", " + col + ")");
            addGridElem(Number(row), Number(col), player_element);
        });
    });
});