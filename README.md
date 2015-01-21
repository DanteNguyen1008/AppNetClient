# AppNetClient
Code challenge sample app

Create an App.net client that just lists the most recent posts from the public timeline.

Posts should be rendered in a ListView or RecyclerView with the most recent at the top.
Each row should contain the user’s avatar (bonus if the image is circular or corners are rounded)
Each row should contain the poster’s name in bold
Each row should contain the post text, and be variable height, depending on the text size
Pull to refresh should be implemented to refresh the timeline
The list should scroll quickly, without dropping frames on a Nexus 5
The latest publicly released Android SDK should be used (bonus if the support library is used to implement Material Design)
Third party libraries are allowed but not required
The timeline can be fetched here: https://alpha-api.app.net/stream/0/posts/stream/global.
