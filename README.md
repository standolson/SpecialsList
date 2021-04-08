# SwiftlyDemo
Swiftly Demo App

The following should satisfy the requirements laid out in Swiftly's Android coding test.

It uses Kotlin, Hilt Dependency Injection, GSON, OkHttp (network), and Glide (image URL loading).  It utilizes ViewModel and LiveData from
Android Jetpack.

Running SpecialsApp will start the app.

I tested this on a phone emulator in both landscape and portrait modes and I believe it is working properly.  I did not have the time to write any tests
though I originally intended to do this.

The app can display the manager's specials either in a one special per row mode or in a way that meets the specification.  Change the value of
SpecialsListFragment.useFlatRecyclerView to true to display one special per row.