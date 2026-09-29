# Plait

Plait er en side- og notatapp bygd som én selvstendig nettside (PWA), laget for å installeres på en Samsung-telefon.

## Filene i denne mappen

| Fil | Hva den gjør |
|---|---|
| `index.html` | Hele appen. All kode, alle skrifttyper og PDF-verktøyet ligger inne i denne ene filen. |
| `manifest.json` | Forteller telefonen navnet, ikonet og fargene til appen når den installeres. |
| `sw.js` | Service worker. Lar appen fungere uten nett og oppdatere seg selv. |
| `icon.svg`, `icon-192.png`, `icon-512.png`, `icon-512-maskable.png` | App-ikonet i ulike størrelser. |
| `.nojekyll` | Tom fil som ber GitHub Pages servere filene direkte, uten å tolke dem som en Jekyll-nettside. |

## Slik legger du det ut på GitHub Pages

1. Last opp **alle filene i denne mappen** til roten av et GitHub-repository (også `.nojekyll` — den er usynlig i noen filutforskere fordi navnet starter med punktum).
2. Gå til **Settings → Pages** i repositoryet.
3. Under **Source**, velg **Deploy from a branch**, velg branchen (som regel `main`) og mappen `/ (root)`.
4. Lagre. Siden blir tilgjengelig på noe sånt som `https://dittbrukernavn.github.io/repositorynavn/`.

## Slik oppdaterer du appen senere

Last opp den nye `index.html` (og eventuelt `sw.js`, hvis den er endret) til samme sted på GitHub, og overskriv de gamle filene. Service workeren sørger for at telefoner som har appen installert fra før, henter den nyeste versjonen neste gang de åpner den.

## Alt lagres lokalt

Sider, avtaler og bilder lagres bare i nettleseren på hver enkelt telefon. Ta jevnlig backup fra menyen øverst til høyre i appen (last ned backup), i tilfelle du bytter telefon, nettleser eller sletter nettleserdata.
