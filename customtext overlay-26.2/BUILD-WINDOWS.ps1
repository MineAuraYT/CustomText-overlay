$ErrorActionPreference = 'Stop'

gradle build --parallel --build-cache
if ($LASTEXITCODE -ne 0) { throw 'Gradle build failed. See the compiler output above.' }

New-Item -ItemType Directory -Force -Path '.\JAR' | Out-Null
$modJar = Get-ChildItem '.\build\libs\*.jar' | Where-Object { $_.Name -notmatch 'sources|dev' } | Select-Object -First 1
if (-not $modJar) { throw 'Build completed but no remapped mod JAR was found in build\libs.' }
Copy-Item $modJar.FullName '.\JAR\' -Force
Write-Host "Mod JAR copied to .\JAR\$($modJar.Name)"
