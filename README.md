"# TooSkunk" 

No documentation yet.

Rule Alpha
--------------
```
IF Age > 18
    THEN print 'Alpha older'
    ELSE print 'Alpha younger'
```

Rule Beta
-----------
```
IF 'Rule Alpha'
    THEN print 'Beta older'
    ELSE print 'Beta younger'
```

Order of execution when running 'Rule Beta'
 * Rule Alpha runs and outputs something.
 * Then depending on the true false status of 'Rule Alpha',
    'Rule Beta' outputs something.


