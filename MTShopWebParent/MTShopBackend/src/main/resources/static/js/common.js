function customizeDropDownMenu() {
    $(".navbar .dropdown").hover(
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    )
    $(".dropdown > a").click(function () {
        location.href = this.href;
    });
}

$(document).ready(function () {
    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropDownMenu();
    customizeTabs();
});

function customizeTabs() {
    var url = document.location.toString();
    // enable link to tab
    if (url.match('#')) {
        $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
    }

    // change has for page-reload
    $('.nav-tabs a').on('shown.bs.tab', function (e) {
        window.location.hash = e.target.hash;
    })
}