$(document).ready(function() {
	var conInnerConWidth = $(".innercon").width();
	var conSize = $(".innercon").size();
	var tabHeight = $(".tab a").height();
	if (!window.cur)
		window.cur = 0;
	$(".tab a").click(function() {
		var index = $(".tab a").index(this);
		slide(conInnerConWidth, tabHeight, index);
		return false;
	});
	$(".prev").click(function() {
		if (window.cur > 0)
			slide(conInnerConWidth, tabHeight, window.cur - 1);
		return false;
	});
	$(".next").click(function() {
		if (window.cur < conSize - 1)
			slide(conInnerConWidth, tabHeight, window.cur + 1);
		return false;
	});
	$(".feature_tour .tab, .feature_tour .nav").addClass("vv");
	function slide(conInnerConWidth, tabHeight, index) {
		$(".tab a").removeClass("current");
		$(".tab a").eq(index).addClass("current");
		$(".tab").css("background-position", "0 -" + index * tabHeight + "px");
		$(".maskcon").animate({
			marginLeft : -index * conInnerConWidth + "px"
		});
		window.cur = index;
	}
});