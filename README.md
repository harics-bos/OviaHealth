# OviaHealth
1)At a high level, how does your app work?<br />
In a nutshell, the app does the following<br />
i)The app allows searching for movies either by typing the movie name or by through an IMDB title.<br />
ii)The search button will be disabled and will get enabled when the search field has text.<br />
iii)The validations are delegated to the Presenter which performs the business logic.<br />
iv)Once the search button is triggered, the presenter interacts with the Model which interacts with the Service and executes it.<br /> 
v)OKHttp is used to enable network interaction.<br />
vi)Once OKHttp gets the response, it delegates the result to the Model.<br />
vii)The Model gets the POJO mapped to the Json response and passes the concrete response object to the presenter.<br />
viii)The Presenter interacts with the view which populates based on the result.<br />
ix)Negative scenarios such as InValid response, Unsuccessful Network response etc.. are also handled and delegated through a callback to the Model.<br />
x)The View shows an appropriate alert dialog presenting the error to the user.<br />
xi)The Model takes care of injecting an appropriate text for the title and the alert description.<br />

2)What decisions did you make, and why?<br />
MVP pattern is employed to enable communication between different layers. MVP facilitates loose coupling between components. The View and Model are completely ignorant of each other which would help the Model getting reused across multiple features as the app evolves. The View is self-functional and doesn't need to be bothered on any change that might happen in future. Similarly, the presenter takes care of the business logic. This clear separation will enable reusability by maintaining strict contracts established through interfaces. This design would be very helpful as the app evolves and would be effective in building a maintainable software.<br />
Also in the Network layer, the template pattern (mapping incoming result) is loosely employed. This would help the individual features to not worry about the complexities involved in establishing a network call, also avoids boilerplate code.<br />

3)What design patterns or architecture might be necessary in the future?<br />
I am not getting a clear picture of how this app is expected to evolve. I could address this better if I could be given specific scenarios on how the app might evolve and the inherent complexities/pain points involved in the process.<br />

4)How would we extend your app if we had to add functionality?<br />
Structurally, the MVP pattern should be given preference. The network interaction can be seamlessly achieved just by adhering to the interfaces I have defined. Kindly take a look at the MovieInfoModel.java. This would be a good starting point to help understand how to add new functionalities. Certain common exceptions, network failures are handled in the network layer. So the feature developer needs to concentrate only on the business logic that's required for the feature.<br />

5)How might you change the architecture of the app if the data model and end point code needed to be reused in a second app?<br />
I would use gradle (product flavours) to have the second app use the same code base (by modifying the Build Variants). For EndPoints, I would create an endpoint json which will follow the below schema<br />
{<br />
  app1:{<br />
             Endpoint key: url,<br />
            .....<br />
            },<br />
    app2:{
          Endpoint key: url,<br />
         ......<br />
            }<br />
}<br />

I will potentially introduce Composite Pattern/tree Structure and will bring an EndPoint Factory (Factory pattern) which will aid in injecting the appropriate Endpoint based on Brand.<br />
The model would stay intact. However, I will use another Factory, ServiceFactory, that will provide the Model with the appropriate service.<br />
 
6)What documentation, websites, papers etc.. did you consult for this assignment?<br />
i)For aspect ratio related details, I referred https://guides.codepath.com/android/Working-with-the-ImageView<br />
ii)For injecting images from network, I referred code examples from<br />
http://square.github.io/picasso/<br />
iii)For hex color codes, I referred https://www.rapidtables.com/web/color/gray-color.html<br />
iv)Android documentation for ui elements used in the layout xml<br />
v)OKHttp documentation for asynchronous service calls: https://github.com/square/okhttp/wiki/Recipes<br />

7)What third­party libraries or other tools does your application use? How did you choose each library or framework you used?<br /> 
i)Picaaso: Have heard rave reviews about its functionalities and ease of use<br />
ii)OKHttp: In my current company,I was part of a team that migrated from Apache Http Client to OKHttp. So I had good exposure to this library. Hence, it was my natural choice.<br />

8)How long did you spend on this exercise? If you had unlimited time to spend on this, how would you spend it and how would you prioritize each item?<br />
I spent approximately close to 5 hrs. If I had unlimited time, I would focus on establishing a strong infrastructure for the app. At a high level, I would do the below:<br />
i)Crash Logger: Will install crash analysis tools such as Crashlytics/ ACRA to keep track of real-time crashes.<br />
ii)Chicken switch features: I would bring in a mechanism of dynamically controlling the features of the app. If there arises serious bug on a feature, I will dynamically turn it off.<br />
iii)Analytics: I will introduce analytics capturing key events in the app. This would drive the business in gaining valuable insights of the app which might help in creating an insightful roadmap.<br />
iv)Code Obfuscation: I will use ProGuard to prevent reverse engineering<br />
v)Securing private key: Rather than hardcoding the private key in the app, I would have a setup by which I would be getting an encrypted token and in the run time I will decode and use the key. This will increase the security of the keys.<br />
vi)Strengthening Network layer: I will add Cache interceptors to cache service response for higher performance. I would also likely add CertificatePinner to make sure I communicate with trusted host.<br />

9)If you were going to implement a level of automated testing to prepare this for a production environment, how would you go about doing so?<br />
I will focus my unit testing scripts mainly on the model. Model is the most portable portion of the application. Then I would write test scripts for the Presenters.<br />
