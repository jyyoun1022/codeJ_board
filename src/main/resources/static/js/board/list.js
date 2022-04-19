var searchForm = $("#searchForm");

$('.btn-search').click(function(){
    searchForm.submit();
});

$('.btn-clear').click(function(){
    searchForm.empty().submit();
});


