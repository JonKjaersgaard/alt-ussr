;;; Imports

;; Java native interface
(import s2j)
;; Various list processing functions
(require-library 'sisc/libs/srfi/srfi-13)
(import srfi-13)
;; from misc.scm
(define (filter pred l)
  (if (pair? l)
      (if (pred (car l))
          (cons (car l) (filter pred (cdr l)))
          (filter pred (cdr l)))
      l))


;;; Basic functionality

; The Java object used to interface to the simulation, obtained using singleton accessor method
(define-java-class <ussr-interface-class> |ussr.extensions.languages.SchemeGadget|)
(define ussr-interface-object ((generic-java-method '|getHost|) (java-null <ussr-interface-class>)))

; Get controller from module
(define-generic-java-method controller-from-hardware |getController|)

; Lookup module by name
(define (module name)
  (controller-from-hardware ((generic-java-method '|findModule|) ussr-interface-object (->jstring name))))

; Get a list of all modules
(define (modules)
  (map controller-from-hardware (->list ((generic-java-method '|getModulesArray|) ussr-interface-object))))

; Get the name of a module
(define (get-name module)
  (->string ((generic-java-method '|getName|) module)))
    
; Test if a module has a given role
; Note: currently implemented by simply testing if the role name is in the name of the module
(define (has-role role)
  (lambda (module)
    (let ([s (if (symbol? role) (symbol->string role) role)])
      (if (string-contains (get-name module) s) #t #f))))

; Map a function onto those modules in the simulation that play a specific role
(define (map-role role f)
  (map f (filter (has-role role) (modules))))

;;; ATRON API

(define-generic-java-method stop |centerStop|)

(define-generic-java-method disconnect-all |disconnectAll|)

(define (rotate-to degrees)
  (lambda (module)
    ((generic-java-method '|rotateDegrees|) module (->jint degrees))))

(define (rotate-continuous direction)
  (lambda (module)
    ((generic-java-method '|rotateContinuous|) module (->jint direction))))

;;; Examples
; destroy car
; (map (rotate-continuous 1) (filter (has-role "wheel") (modules))
