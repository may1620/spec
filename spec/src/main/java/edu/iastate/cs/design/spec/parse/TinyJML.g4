grammar TinyJML;

@header { package edu.iastate.cs.design.spec.parse; }

tiny_specification : COMMENT_OPEN JML_ANNOTATION WS jml_requires WS
                                  JML_ANNOTATION WS jml_ensures WS
                                  JML_ANNOTATION COMMENT_CLOSE ;

jml_requires : REQUIRES WS expression ;

jml_ensures : ENSURES WS expression ;

expression : NUMBER WS* EQUAL WS* NUMBER ;

COMMENT_OPEN : '/*' ;

COMMENT_CLOSE : '*/';

JML_ANNOTATION : '@' ;

ENSURES : 'ensures' ;

REQUIRES : 'requires' ;

EQUAL : '=' ;

NUMBER : ('0' .. '9')+ ;

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ ;