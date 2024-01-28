grammar PolicyExpression;

policyExpression
    : expression EOF
    ;

expression
    : jsonValueLiteral
    | jsonExpression
    | NOT expression
    | expression AND expression
    | expression OR expression
    | expression EQ expression
    ;

// LITERAL VALUE
jsonValueLiteral
    : NUMBER
    | STRING
    | TRUE
    | FALSE
    | NULL
    ;


// JSON
jsonExpression
    : 'P' jsonSelector
    | 'R' jsonSelector
    ;

jsonSelector
    : '.' ID ('.' ID)*
    | '[\'' ID '\']' ('[\'' ID '\']')*
    ;


WS: [ \t\n\r\f]+ -> skip ;

AND : 'and' ;
OR : 'or' ;
NOT : 'not' ;
EQ : '==' ;
TRUE : 'true' ;
FALSE : 'false' ;
NULL : 'null' ;

ID: [a-zA-Z_][a-zA-Z_0-9]* ;


// JSON LITERAL
STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   ;
fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE)
   ;
fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;
fragment HEX
   : [0-9a-fA-F]
   ;
fragment SAFECODEPOINT
   : ~ ["\\\u0000-\u001F]
   ;

NUMBER
   : '-'? INT ('.' [0-9] +)? EXP?
   ;
fragment INT
   // integer part forbis leading 0s (e.g. `01`)
   : '0' | [1-9] [0-9]*
   ;
// no leading zeros
fragment EXP
   // exponent number permits leading 0s (e.g. `1e01`)
   : [Ee] [+\-]? [0-9]+
   ;
// \- since - means "range" inside [...]