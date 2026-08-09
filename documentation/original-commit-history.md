# Original Commit History (Preserved Reference)

During cleanup, the Renter app and User app were each found to contain their
own independent local git repository (nested inside the project tree, not
git submodules). Since all four apps were reorganized into a single
repository, these nested `.git` directories were removed — but their commit
history is preserved here for reference before deletion.

No secret values appear in this log (git's `--stat` output only lists
changed file paths and line counts, never file contents), so it's safe to
keep in full.

---

## Renter app (originally `CarOnRent(Renter)/CarOnRent(Renter)`)

Original remote: `https://github.com/andprj7/Car-Rent_Renter.git`

```
commit 1064297fd6b56582201d45affe73eace0c6b604e
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Wed Dec 27 19:40:35 2023 +0530

    Shimmer Effect

 .../example/caronrentrenter/FavoriteFragment.java  |  16 +++
 app/src/main/res/layout/fragment_favourite.xml     |  43 +++++++-
 .../res/layout/my_recycler_item_placeholder.xml    | 109 +++++++++++++++++++++
 3 files changed, 166 insertions(+), 2 deletions(-)

commit 10cebf482fdbbfbee915b65f3e74dd71d49e1767
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Wed Dec 27 19:07:56 2023 +0530

    Shimmer Effect

 .idea/compiler.xml                                 |   6 -
 .idea/deploymentTargetDropDown.xml                 |  13 --
 .idea/gradle.xml                                   |  19 ---
 .idea/migrations.xml                               |  10 --
 .idea/misc.xml                                     |   9 --
 .idea/vcs.xml                                      |   6 -
 app/build.gradle.kts                               |   3 +
 .../com/example/caronrentrenter/HomeFragment.java  | 158 ++++-----------------
 app/src/main/res/layout/fragment_home.xml          |  72 +++++++++-
 app/src/main/res/layout/my_item_placeholder.xml    | 102 +++++++++++++
 10 files changed, 201 insertions(+), 197 deletions(-)

commit 2d6bfab9fbe60eec23523f87407c55cdc3db222f
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Tue Dec 26 16:33:40 2023 +0530

    MEET RAMANI

 (84 files changed, 1768 insertions(+), 4219 deletions(-) — asset/icon churn and new Compass/HomeFragment/ProfileFragment/etc. features)

commit 19da47cb2b0082377fbafd1c349675f3f04ced98
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 16:52:55 2023 +0530

    MEET RAMANI

 app/src/main/AndroidManifest.xml                          |  5 +++++
 app/src/main/java/com/example/caronrentrenter/Detail.java | 15 ++++++++++++++-
 2 files changed, 19 insertions(+), 1 deletion(-)

commit 191d316188f822368dc3882d3a43995c001b05e4
Merge: 36c968c 2d4b4b5
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 14:30:50 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit 36c968c9600d4979e704a1b26ee79e5a1fe81a8d
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 14:30:10 2023 +0530

    MEET RAMANI

 .../main/java/com/example/caronrentrenter/Detail.java  | 18 ++++++++++++++++++
 1 file changed, 18 insertions(+)

commit 2d4b4b5407593a78fa9aa32435e6dc7bb6fe83b8
Merge: 51d8d77 16293c9
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Fri Dec 22 14:15:18 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit 51d8d77eb092c9523af1c59476590c01894e8354
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Fri Dec 22 14:14:56 2023 +0530

    car_add

 (33 files changed, 154 insertions(+) — asset icons for favorite/history/home/settings/wallet)

commit 6bb970da273d6424a5bcdaa9221bb13d4676f4cf
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Fri Dec 22 14:11:39 2023 +0530

    car_add

 app/src/main/res/values/ic_launcher_background.xml | 4 ++++
 1 file changed, 4 insertions(+)

commit 81cd41720f335e3b63e91ee00541c54f61625b78
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Fri Dec 22 14:10:44 2023 +0530

    car_add

 (5 launcher-icon webp files added)

commit 87443e2172adaec1f77a898998afca6dfaf75205
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 14:07:24 2023 +0530

    MEET RAMANI

 app/src/main/java/com/example/caronrentrenter/Date_Book.java | 1 +
 1 file changed, 1 insertion(+)

commit 16293c94cd8ee152864c2972e3f87e77899c8396
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 13:53:58 2023 +0530

    M

 (64 files changed, 4241 insertions(+), 57 deletions(-) — icon/drawable churn, Date_Book feature)

commit 9762ab2b97c825a5e6ce487f828bb52f04b0710b
Merge: 36663d1 aa3f3d7
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 22 08:25:21 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit aa3f3d74dd65d34354c2b8412ce8d8e4b7f9c13f
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Fri Dec 22 00:03:32 2023 +0530

    car_add

 app/src/main/AndroidManifest.xml                   |   7 +-
 .../com/example/caronrentrenter/Date_Book.java     |  16 +++
 app/src/main/res/layout/activity_date_book.xml     | 141 +++++++++++++++++++++
 3 files changed, 160 insertions(+), 4 deletions(-)

commit 36663d1ee78354b12c9b4def5ad076420f246351
Merge: e6dc9f1 7174e29
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Thu Dec 21 22:06:10 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit e6dc9f1bcde20065261aaa0d79c3f07c258a6ccf
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Thu Dec 21 22:05:33 2023 +0530

    M

 app/src/main/res/drawable-xxhdpi/profile.png | Bin 0 -> 128473 bytes
 1 file changed, 0 insertions(+), 0 deletions(-)

commit 7174e29476e8eca1af4100aa0daab670fb2bd22c
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Thu Dec 21 16:45:32 2023 +0530

    car_add

 (63 files changed, 2542 insertions(+), 518 deletions(-) — Car_Menu, EditProfile, Profile_Ui, Favorite, Detail features added)

commit 441b961ebfe8308f675d8c27961f209db181d6a6
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Thu Dec 14 13:15:59 2023 +0530

    car_add

 (13 files changed, 423 insertions(+), 65 deletions(-) — CarDetailAdapter, DetailClass added)

commit d303bdbabbe0fa2539fe78165cad87d2ff2428a6
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Tue Dec 12 16:36:08 2023 +0530

    id changed

 app/src/main/res/layout/activity_detail.xml | 146 ++++++++++++++--------------
 1 file changed, 71 insertions(+), 75 deletions(-)

commit 3e9a0d70e9ab3c94d511dc8ad418f93700d296e7
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Tue Dec 12 00:37:23 2023 +0530

    car_add

 (18 files changed, 677 insertions(+), 262 deletions(-) — RenterAdapter, car detail drawables)

commit 8fd5da51eeddf10678a05d669cd2aeabf5d0217b
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Mon Dec 11 18:24:48 2023 +0530

    car_add

 (4 files changed, 161 insertions(+), 53 deletions(-) — ItemAdapter, ItemDomain)

commit bc967d453e20c188c293a49671ebca3bef112991
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Mon Dec 11 16:43:32 2023 +0530

    car_add

 (16 files changed, 992 insertions(+), 9 deletions(-) — Detail activity, ItemAdapter/ItemDomain added)

commit 7fd9bef3e060c805f2900ce244b448ac46c44790
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Sat Dec 9 17:51:47 2023 +0530

    car_add

 (19 files changed, 905 insertions(+), 16 deletions(-) — Car_item_add, DataClass added)

commit c45aa8adf37a759268926f424b6d0e8d3634feb9
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 8 15:56:56 2023 +0530

    "MEET RAMANI"

 4 files changed, 219 insertions(+)

commit 14686db90faadb1207ff0e118cfd852af6c51666
Merge: d5bf9e4 ea9e802
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 8 15:56:30 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit d5bf9e497525bee496b38cf324cb3d4ac13ad8eb
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 8 15:55:49 2023 +0530

    "MEET RAMANI"

 11 files changed, 335 insertions(+), 53 deletions(-)

commit ea9e8026cbbf4c39e39cd9820bc66c637e88e2c5
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Fri Dec 8 14:28:00 2023 +0530

    New

 app/src/main/res/layout/activity_login.xml   | 7 ++++---
 app/src/main/res/layout/activity_sign_up.xml | 1 +
 2 files changed, 5 insertions(+), 3 deletions(-)

commit fa26dbc8555cdded630f9b6de1b3a7c9b420a25a
Merge: 1e6835c 942b9d7
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 8 14:12:37 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit 942b9d7bf70bcb65815654623887e60280351e53
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Fri Dec 8 14:12:07 2023 +0530

    New

 24 files changed, 126 insertions(+), 5 deletions(-)

commit 881e2afa104dbe311149bd0e011571f32cdecf8b
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Fri Dec 8 12:44:22 2023 +0530

    New

 33 files changed, 1896 insertions(+), 67 deletions(-)
 (includes: app/google-services.json added — 49 lines, file listing only, no content shown here)

commit 1e6835c2779bc75e360f350e445a240ba651d110
Merge: 01760c0 c69baf6
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 17:21:24 2023 +0530

    Merge remote-tracking branch 'origin/master'

    # Conflicts:
    #       .idea/gradle.xml
    #       .idea/misc.xml
    #       .idea/vcs.xml

commit 01760c094781f140e447299622b7c4cbcbe83980
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 17:20:41 2023 +0530

    MEET RAMANI

 7 files changed, 63 insertions(+)

commit c69baf60c669f6e1bae69ee96b76046b7c8e91e6
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Sat Dec 2 16:07:12 2023 +0530

    Registration for Renter

 26 files changed, 585 insertions(+), 4 deletions(-)

commit 9f490851eebbb7321fcf95689dedbc9bc1b68564
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 08:22:09 2023 +0530

    MEET RAMANI

 app/src/main/res/layout/activity_main.xml | 11 +++++++++++
 1 file changed, 11 insertions(+)

commit 495a513f146a954ca0e887db4397183ede877b5c
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 08:21:01 2023 +0530

    MEET RAMANI

 app/src/main/res/layout/activity_main.xml | 2 +-
 1 file changed, 1 insertion(+), 1 deletion(-)

commit a15c1e06e568876b5224c1255aaad8cdb2a5639a
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 08:19:11 2023 +0530

    first commit

 36 files changed, 771 insertions(+)
 (initial Android Studio project scaffold)
```

---

## User app (originally `Car-Rent/Car-Rent`)

Original remote: `https://github.com/andprj7/Car-Rent.git`

```
commit ca4430acae6a3cf12af8c75cfda114aee426b66c
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Thu Dec 21 16:50:42 2023 +0530

    ...

 53 files changed, 3348 insertions(+), 318 deletions(-)
 (FavoriteAdapter, ItemAdapter, DataClass, Detail_Of_Car, EditProfile, Favorite,
  Login, MainActivity, Profile_Ui, SignUp — major feature build-out)

commit d5021cc1587fb548409465d3743a29b3b03d8215
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Thu Dec 7 14:08:27 2023 +0530

    ...

 4 files changed, 299 insertions(+), 235 deletions(-)

commit 6ec460fd9d8b55c9b3dff8ef4bd746a82a12dc43
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Thu Dec 7 09:45:44 2023 +0530

    ...

 8 files changed, 216 insertions(+), 76 deletions(-)

commit 5b360522a39bc92ad7ce76b433c6405687ef37d7
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Tue Dec 5 20:16:53 2023 +0530

    ...

 app/src/main/java/com/example/caronrent/SignUp.java | 3 +++
 1 file changed, 3 insertions(+)

commit f8a1f7ef34950a13dfd20006872d3b4b85ef8f35
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Tue Dec 5 20:05:32 2023 +0530

    ...

 3 files changed, 1 insertion(+), 1 deletion(-)

commit 62fa139eb8394b61f18278a9fbd68627f55284f2
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Tue Dec 5 12:43:43 2023 +0530

    ...

 11 files changed, 345 insertions(+), 84 deletions(-)

commit e10e493574bbdc6c88c800edbbfbd2262a8200e5
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Tue Dec 5 12:36:11 2023 +0530

    changes

 app/src/main/java/com/example/caronrent/Login.java |  8 +++++---
 app/src/main/res/layout/activity_login.xml         | 20 +++++++++++---------
 2 files changed, 16 insertions(+), 12 deletions(-)

commit ad0b1a6a7dbd90309b22c5114211885eb6076832
Merge: 8f43fd5 0abe523
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Tue Dec 5 12:21:23 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit 8f43fd53fdd8205d55ca2c8be24657824ce37467
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Tue Dec 5 12:20:46 2023 +0530

    MEET RAMANI

 7 files changed, 640 insertions(+), 6 deletions(-)
 (Favorite, History, Home, Settings, Watch activities added)

commit 0abe52301eee4b525a8235877b0f1eda98740587
Merge: 8934987 ed1db67
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Tue Dec 5 12:11:00 2023 +0530

    Merge remote-tracking branch 'origin/master'

    # Conflicts:
    #       app/src/main/res/layout/activity_login.xml

commit 8934987b8b201489d114d133acc1c19c0a7a0290
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Tue Dec 5 12:09:58 2023 +0530

    changes

 6 files changed, 208 insertions(+), 34 deletions(-)

commit ed1db67d743f31d313d033cfd2677b0a1a37570d
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Mon Dec 4 15:08:17 2023 +0530

    MEET RAMANI

 app/src/main/java/com/example/caronrent/SignUp.java | 4 ++--
 1 file changed, 2 insertions(+), 2 deletions(-)

commit aeabd279d1bc073ce66d894acdc11639f8de4ff7
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Mon Dec 4 14:46:57 2023 +0530

    MEET RAMANI

 4 files changed, 63 insertions(+), 3 deletions(-)

commit 42f2989cda8698025ba56dddb924bfa19f92c3d2
Merge: 97a9dc2 acc3b57
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Mon Dec 4 14:10:37 2023 +0530

    Merge remote-tracking branch 'origin/master'

commit 97a9dc2688e68dab5ef2e775836f8a73d4d91ee1
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Mon Dec 4 14:10:09 2023 +0530

    MEET RAMANI

 app/src/main/res/layout/activity_login.xml | 4 +++-
 1 file changed, 3 insertions(+), 1 deletion(-)

commit acc3b579bc04d1ce630383bf466349f2648f9db4
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Mon Dec 4 12:00:22 2023 +0530

    changes

 6 files changed, 213 insertions(+), 30 deletions(-)

commit a500a2ead0067f64471c3a7769be8466ee35c953
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 22:15:23 2023 +0530

    MEET RAMANI

 app/src/main/res/layout/activity_location.xml | 4 +++-
 1 file changed, 3 insertions(+), 1 deletion(-)

commit 38cd30e9d9d9643d983f244dbee60bebf8dfa4d8
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 22:05:48 2023 +0530

    MEET RAMANI

 .idea/deploymentTargetDropDown.xml | 22 ----------------------
 1 file changed, 22 deletions(-)

commit f618588c9cc9681c2459b9c2a49e3cce5a32c91a
Merge: 0956cd8 52aeff9
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 22:05:17 2023 +0530

    MEET RAMANI

commit 0956cd85b9834b5a4af1ad3bea18a270a1376251
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Sat Dec 2 21:58:17 2023 +0530

    MEET RAMANI

 10 files changed, 199 insertions(+), 10 deletions(-)
 (Loc.java, activity_location.xml added)

commit 52aeff9d3b5d9d1875ca8f9a5653c24caaccf5e4
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Sat Dec 2 18:04:41 2023 +0530

    changes

 4 files changed, 68 insertions(+), 2 deletions(-)
 (otp_verify.java added)

commit d5d9f3538542962f2798a3e3f1d5996e7b2d1a38
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Sat Dec 2 17:43:16 2023 +0530

    changes

 2 files changed, 5 insertions(+), 6 deletions(-)

commit 962e81747fb755ebfa607e5596c99aee6c515f12
Author: dharmika-shakti <dharmika.shaktiwebsolutions@gmail.com>
Date:   Sat Dec 2 17:25:49 2023 +0530

    FIrebase added

 7 files changed, 66 insertions(+), 6 deletions(-)
 (app/google-services.json added — 29 lines, file listing only, no content shown here)

commit 241b0cdcdab63a23943a17da54e4e99d5572719a
Author: Devang-Sarvaiya <devangsarvaiya99091@gmail.com>
Date:   Sat Dec 2 15:42:41 2023 +0530

    Registration of Customer

 14 files changed, 388 insertions(+), 5 deletions(-)

commit 067ab87150b155e31bfd88b9ac66d413714d4750
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 1 17:45:10 2023 +0530

    MEET RAMANI

 12 files changed, 154 insertions(+), 16 deletions(-)

commit cf8c013056fc6da6e4184d7e5a8b64a3769dcbdb
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 1 15:16:09 2023 +0530

    Demo

 .idea/compiler.xml | 6 ++++++
 .idea/vcs.xml      | 6 ++++++
 2 files changed, 12 insertions(+)

commit d545efe415fa891551b2114d9288f6c7c5b87ac8
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 1 15:14:55 2023 +0530

    Demo

 3 files changed, 15 insertions(+), 1 deletion(-)

commit aedef9888f15ede0a3f8c598eb7d0dcc40316295
Author: RAMANI MEET <meetgramani6697@gmail.com>
Date:   Fri Dec 1 15:06:30 2023 +0530

    1st

 40 files changed, 799 insertions(+)
 (initial Android Studio project scaffold)

Remotes:
origin  https://github.com/andprj7/Car-Rent.git (fetch)
origin  https://github.com/andprj7/Car-Rent.git (push)
```
