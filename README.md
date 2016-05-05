"# TooSkunk" 

No documentation yet.

Rule Alpha
--------------
```
IF Age > 18
    THEN print 'older'
    ELSE print 'younger'
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
    'Rule Beta' then outputs something.


