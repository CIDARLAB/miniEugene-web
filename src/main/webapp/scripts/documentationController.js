  $( document ).ready(function() { // loads once the document is ready
    
    // the variables that will hold all of the content
    var $what_is = $('<div>'); 
    var $grammar = $('<div>');
    var $comments = $('<div>');
    var $constraints = $('<div>'); 
    var $counting_constraints = $('<div>'); 
    var $positioning_constraints = $('<div>'); 
    var $pairing_constraints = $('<div>'); 
    var $orientation_constraints = $('<div>'); 
    var $interaction_constraints = $('<div>'); 
    var $cnf = $('<div>'); 
    var $templates = $('<div>'); 
    var $examples = $('<div>'); 
    
    $what_is.load("documentationFiles/what_is.html");

    $comments.load("documentationFiles/comments.html");
    $grammar.load("documentationFiles/grammar.html");

    $constraints.load("documentationFiles/constraints.html");
    $counting_constraints.load("documentationFiles/counting_constraints.html");
    $positioning_constraints.load("documentationFiles/positioning_constraints.html");
    $pairing_constraints.load("documentationFiles/pairing_constraints.html");
    $orientation_constraints.load("documentationFiles/orientation_constraints.html");
    $interaction_constraints.load("documentationFiles/interaction_constraints.html");

    $cnf.load("documentationFiles/cnf.html");
    
    $templates.load("documentationFiles/templates.html");

    $examples.load("documentationFiles/examples.html");

    //$("#content").html($what_is); // loads the initial content
    //$("#what_is").addClass('active');

    // the various click events that will switch tabs and data
    $("#what_is").click(function(){ 
    	$("#content").html($what_is);
    	$(".active").removeClass('active');
    	$("#what_is").addClass('active');
    	$( window ).scrollTop(0);
    });


    $("#comments").click(function(){ 
    	$("#content").html($comments);
    	$(".active").removeClass('active');
    	$("#comments").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#grammar").click(function(){ 
    	$("#content").html($grammar);
    	$(".active").removeClass('active');
    	$("#grammar").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#constraints").click(function(){ 
    	$("#content").html($constraints);
    	$(".active").removeClass('active');
    	$("#constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#counting_constraints").click(function(){ 
    	$("#content").html($counting_constraints);
    	$(".active").removeClass('active');
    	$("#counting_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#positioning_constraints").click(function(){ 
    	$("#content").html($positioning_constraints);
    	$(".active").removeClass('active');
    	$("#positioning_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#pairing_constraints").click(function(){ 
    	$("#content").html($pairing_constraints);
    	$(".active").removeClass('active');
    	$("#pairing_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });
    
    $("#orientation_constraints1").click(function(){ 
    	$("#content").html($orientation_constraints);
    	$(".active").removeClass('active');
    	$("#orientation_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#orientation_constraints").click(function(){ 
    	$("#content").html($orientation_constraints);
    	$(".active").removeClass('active');
    	$("#orientation_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#interaction_constraints").click(function(){ 
    	$("#content").html($interaction_constraints);
    	$(".active").removeClass('active');
    	$("#interaction_constraints").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#cnf").click(function(){ 
    	$("#content").html($cnf);
    	$(".active").removeClass('active');
    	$("#cnf").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#templates").click(function(){ 
    	$("#content").html($templates);
    	$(".active").removeClass('active');
    	$("#templates").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#examples").click(function(){ 
    	$("#content").html($examples);
    	$(".active").removeClass('active');
    	$("#examples").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#examples1").click(function(){ 
    	$("#content").html($examples);
    	$(".active").removeClass('active');
    	$("#examples").addClass('active');
    	$( window ).scrollTop(0);
    });

    $("#constraints1").click(function(){ 
    	$("#content").html($constraints);
    	$(".active").removeClass('active');
    	$("#constraints").addClass('active');
    	$( window ).scrollTop(0);
    });
    
    $("#templating1").click(function(){ 
    	$("#content").html($templates);
    	$(".active").removeClass('active');
    	$("#templates").addClass('active');
    	$( window ).scrollTop(0);
    });

  });
  
  $("#constraintLink").click(function() {
	  var $constraints = $('<div>');
	  $constraints.load("documentationFiles/constraints.html");
	  $("#content").html($constraints); // loads the initial content
	  $("#constraints").addClass('active');
  });