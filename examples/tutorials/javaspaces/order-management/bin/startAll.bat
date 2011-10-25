@echo off

@rem Setting example environment(%JSHOMEDIR%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

@rem Starting the space
@rem
@rem %JSHOMEDIR%    - GigaSpaces root dir.
@rem
@rem \bin\gsInstance    - Batch file to start GigaSpaces space instance.
@rem
@rem /./    - The . sets the space container name to be the same as the space name.
@rem
@rem oms    - The space name.
@rem
@rem groups=%LOOKUPGROUPS%  - Sets the jini lookup service groups (%LOOKUPGROUPS% is set in 
@rem                        <GigaSpaces Root Dir>\bin\SetEnv.bat called by setExampleEnv.bat).

@call "%JSHOMEDIR%\bin\gsInstance" "/./oms?groups=%LOOKUPGROUPS%"

