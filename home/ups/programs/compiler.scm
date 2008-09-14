(define parse-role (role)
  (if (eq? (car role) 'program)
      (let ([name (cadr role)]
	    [super (caddr role)]
	    [invariants (map parse-invariant (cadddr role))]
	    [constants (map parse-constant (cadddr role))])
	(string-append
	 (new "Role" (list (as-string name) ","
			   (as-string super) ","
			   (as-array 
		      

(define (rdcd programName initialCompass program)
  (let ([roles (map parse-role (cdr program))]
	[deployment (parse-deployment (cdr program))])
    (display (as-array roles))
    (display ",\n")
    (display deployment)))
