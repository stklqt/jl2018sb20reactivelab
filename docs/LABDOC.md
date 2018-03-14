# Reactive Microservices with Spring Boot 2.0 and Cassandra - Workshop documentation

## 1. Setup

1. Go to https://start.spring.io/ and configure your Spring Boot project. Choose 2.0.0.RC2 as a version and
as dependencies `Reactive Web` and `Cassandra`. This will add both the cassandra driver as well as the spring-data-cassandra module.
Optionally add `Lombok` too as a dependency.

Alternatively you can choose to clone the master branch of this (https://github.com/meehighd/reactive-lab.git) repository.

2. Import the generated or cloned project into IntelliJ IDEA (File->New->Project from existing sources...) using the provided pom.xml.
Take a look at the generated pom file to see the added dependencies.

3. Start the application using the main generated class, `de.virtual7.reactivelab.ReactiveLabApplication`

4. Copy the Cassandra distribution archive from `tools/apache-cassandra-3.11.1-bin.tar.gz`, unarchive it and
start it by executing `bin/cassandra`. Make sure it's up and running before starting the CQL client with `bin/cqlsh`:
     - Execute `describe keyspaces` command to see what keyspaces there are;
     - After starting the application for the first time you will be able to see your own keyspace listed;
     - With `use your_keyspace_name` you can switch to your keyspace and consequently run CQL commands against it;

## 2. Explore the project

1. Navigate through the project to get to know the structure. Notice the resources folder where the application.properties resides.

## 3. Get to know the reactive API

 Mono<T> is a publisher which can emit 0 or 1 items.
 Flux<T> on the other hand can emit between 0 and infinite elements.
 Both types allow you to lay the plan so to say of what should happen when a client application subscribes to them. So again, nothing happens until you subscribe!

2. Get to know the Mono
    - Go to `de.virtual7.reactivelab.basics.MonoTests` and try to solve the TODOs.
    - `testCreateVoidMono`: Firstly, create Mono without a value, containing just a Void. This scenario could be useful for example when you want to trigger
    something but you're not interested in the result.
     Careful, a Mono<Void> doesn't mean there is no Mono emitted, it just means the Mono instance does not hold anything. Use `Mono.empty()`
     and call `.log()` before subscribing to it, to see what happens under the hood.
    - `testCreateScalarMono`: Create a Mono holding a single value by calling Mono.just.
    - `testFlatMapMono`: Create a Mono by calling Mono.just("Hello,") and then transform it by calling the flatMap operator, emitting in the end the "Hello, World" string.
    - Call method but do not subscribe to it. Does it get called ? Use a `CountDownLatch` to await for its completion
    - `testCreateFluxFromMono`: Convert a `Mono` to a `Flux`. Let's suppose we read a list of strings from a non-reactive source, e.g a non-reactive REST-Service or a database.
      - Step 1. Create an instance of `List<String>`
      - Step 2. Convert it to a `Mono` using `Mono.just()`
      - Step 3. Convert the freshly created `Mono` instance to a `Flux` using `mono.flatMapToMany(Flux::fromIterable)`. Subscribe to it by calling `.subscribe(System.out::println)`
    - `testMergeMonos`: Merge two `Mono` instances
    - `testZipMonos`: Zip two Monos, what do you get back ? How many Monos can you zip together?
    - `testBlockingMono`: What would be the use of Mono.block ? Write a test to show its possible usage.
    - Throw exception. Exceptions are first class citizens and they're wrapped in a Mono or Flux, being returned as a perfectly normal value. Make sure to release the latches!


3. ..and the Flux
    - This time go to de.virtual7.reactivelab.basics.FluxTests and try to solve the TODOs there:
    - `testCreateFluxJust`: Create a Flux instance by calling the just operator and by doing so creating a Flux holding a scalar value.
    - `testCreateFluxFromList`: This test could be useful when bridging to a non-reactive API, to transform a list into a `Flux`.
    - `testFluxCountElements`: Create a `Flux` with a couple of elements and call count on it in order to see how many elements it has. What do you get back as return value when you call `.count()`?
    - `testFluxRange`: Here you have to create `Flux` instance using the range operator.
    - `testCreateFluxUsingGenerate`: Using generate you can generate one-by-one elements and you also have access to the current state of the generator, allowing you to decide what to emit next.
    - `testCreateFluxUsingInterval`: Write a test which generates a Flux by using `Flux.interval` method - method which emits a value at a specified interval. This will always
    create a hot `Flux`.
    - `testCreateFluxFromStream`: `Flux.fromStream()` allows you to generate a Flux from a Stream. The source of the stream can be anything: a string, a list, a file, a database...
    - `testZipThem`: Let's zip two `Flux` instances together. Create one `Flux<TrackingEvent>` using like in the following snippet:
    ```java
    Flux<TrackingEvent> eventFlux = Flux.fromStream(Stream.generate(() -> new TrackingEvent(Instant.now())));
    ```
    Create the TrackingEvent class with a single field `Instant eventInstant`. Add the necessary Lombok annotations.
    Create another Flux using `Flux.interval(Duration.ofSeconds(1))`. Zip them together using `Flux.zip()`. What will happen ?
    The zip operator waits for both `Flux` instances to emit a value and then will combine the values in a `Tuple2` instance.
    - `testSwitchIfEmpty`: The `switchIfEmpty` operator allows you to opt for another Publisher in case the current Flux is empty. Try to write a test using `switchIfEmpty`.
    - `testHandleAndSkipNulls`: The `handle` operator allows you to do a 1-to-many mapping including custom logic for each element. Create a `Flux` using range and for each element call
    a method which will return null for some of them. In your handle logic skip the elements for which the the method returns null and go to next element.
    - `testFluxOnErrorResume`: This test is meant to show you two very important operators: `onErrorReturn` and `onErrorResume`.

## Example Service no 1 - Generator / Publisher

 For our workshop we will need two REST services, a producer service and a consumer. Let's start with the producer for now, which will produce tracking events for let's say a
 sorting facility.
 Follow the 1-2 steps to generate a new Spring Boot project or use this project.

 - Add a new Controller to the project or if you cloned the project use the already existent `EventController` class and make sure you add the `@RequestMapping`
 and `@RestController` annotations. Use `/events` as path.

    - Add a method with the `public Flux<TrackingEvent> getEvents()` signature and leave the path specifier empty so it will default as a method when the controller gets called.
 Use `MediaType.APPLICATION_JSON_VALUE` as a content type for the returned results.

 - Extend the `TrackingEvent` class created in the previous step with the following fields:
        - `Long eventID`
        - `TrackingEventType eventType`
        - `BigDecimal eventValue`
        - `Instant eventInstant`

 - Create the TrackingEventType enum with the following values: ARRIVED, PROCESSING, DISPATCHED and DELIVERED. Add the following static method so we can generate random type for our example:
 ```java
     public static TrackingEventType getRandomType() {
         return values()[(int) (Math.random() * values().length)];
     }
```
 - Implement the `getEvents` method so that will generate an infinite stream of events, with an interval of 1 second between events. Hint: use the `Flux.interval` method.
 - Test with curl or paste http://localhost:8080/events in browser. Does it work ? If not, can you guess why ?
 - Add the `text/event-stream` or `MediaType.TEXT_EVENT_STREAM_VALUE` content type and test again.
 - Add another method `public Flux<TrackingEvent> getLatestEvents()` to return only 100 events. Use `Flux.range` in order to do so. Use /latest as a path.
 - Add a `/{eventId}` method to return a single (fake, of course) instance of a `TrackingEvent`. What return type should the method have? But content type?
 - Create a test and test the added methods.
 - Start the application and test the endpoints using curl or directly in browser.

## Example Service no 2 - Consumer / Subscriber

  The consumer service will subscribe to the previously created generator service using the reactive WebClient, will read and interpret the tracking events and eventually will persist
  them in a Cassandra table.

  - First, go to start.spring.io and generate a project with the following properties:
    - Group: de.virtual7
    - Artifact: reactive-lab-client
    - Dependencies: Reactive-Web, Cassandra and optionally Lombok.
  - Alternatively you can clone the master branch from the https://github.com/meehighd/reactive-lab-client.git project which will provide you with the skeleton structure used for the client.

  - Because we already have an application running on port 8080 we have to change the port the client is running on. For this you have to edit the file `resources/application.properties`
  and add the following property there: `server.port=8081`
  - Start `de.virtual7.reactivelabclient.ReactiveLabClientApplication` and make sure it's running.
  - Create a class named `CassandraConfiguration` and add the `@Configuration` annotation. Extend the `AbstractCassandraConfiguration` class and implement for now only the
   `getKeyspaceName` method. Try to run the application and observe the error. What message do you get ? Should be `Keyspace 'reactive_lab' does not exist` because
   your keyspace does not exist yet, of course.
  - In order for your keyspace to get created you must override the `getKeyspaceCreations` method. Use the something similar to the snippet to implement it:
  ```java
          final CreateKeyspaceSpecification specification =
                  CreateKeyspaceSpecification.createKeyspace(...)
                    ...
          return Collections.singletonList(specification);
  ```
  - Start the CQL client from your Cassandra installation folder `bin/cqlsh` and issue again the command `describe keyspaces`, you should see your newly created keyspace there.
  - You could also try to explore the keyspace by typing `use reactive_lab` to switch to it and then `describe tables`
  - Now that we have the database set up we can go on implementing the business logic of our application. Let's create a controller and try to get the events from our publisher. Create
  or use the provided `de.virtual7.reactivelabclient.controller.ClientEventController` class and add a method `@GetMapping("/events") public String getTrackingEvents()`. We'll use for the
  moment String as a return type.
  - Because we're lazy let's copy the `TrackingEvent` and `TrackingEventType` into this project, let's say in the `event` folder. Do not change them for now.
  - Go back in the controller class and implement the getTrackingEvents method so you can receive the top events from `http://localhost:8080/events/latest`. In order to do so you need
  to use the reactive Spring WebClient.  Create an instance of it by using `WebClient webClient = WebClient.create("http://localhost:8080")`
  - Try to use the WebClient instance created in the previous step to retrieve the top events as a Flux you can subscribe to.
  - Try to subscribe to the Flux instance using a custom subscriber, that is a subclass of `BaseSubscriber`. What methods you must override ? Idea: use the custom subscriber to set
   backpressure. Start with a request of one element.
  - Use the `hookOnNext` method to save the received events in a database. In order to keep the business logic separated from the controller code we'll create a service class. Create
  a service package and add a class named `TrackingEventClientService` to it. Annotate it with `@Service` and add a method to it, `public void saveEvent(TrackingEvent event)` and leave it
  empty for the moment. Autowire it in controller and call  `TrackingEventClientService.saveEvent()` in `hookOnNext` with the corresponding event instance.
  - Because there's no way for the moment to persist the events we got let's create a repository for the data we want to save by creating an interface and by extending the CassandraRepository<T, ID>.
  What class should the type from <T, ID> have ? But the ID ?
  - Inject the newly created repository in the `TrackingEventClientService` class and call it's save method in saveEvent.
  - Start the application and call the http://localhost:8081/client/events endpoint. Does it work ? Observe the error, it's most likely something like the following: `InvalidQueryException: unconfigured table trackingeven`
  - The reason for that is that `TrackingEvent` is not configured as a table so Spring does not know where and how should be persisted. To remediate this error we have to add a couple
  of annotations on it. Open the `TrackingEvent` class and add `@Table("events")` annotation at a class level. Every persisted entity needs also a primary key, so let's say in our case that
  our primary key will be a compound key, consisting of a partition key (eventId) and a clustering key (eventType). To configure them as such add annotations as in the following code excerpt:
  ```java
      @PrimaryKeyColumn(...
      private Long eventID;
      @PrimaryKeyColumn(...
      private TrackingEventType eventType;
  ```
  Would it work only with a PrimaryKey annotation ?
  - Start again the application and test using the same url as above. Does it work now ? If still doesn't what error do you get ?
  - In order for your schema to get created you must override the `getSchemaAction` method in your cassandra configuration class. Make it return `SchemaAction.CREATE_IF_NOT_EXISTS` and restart the application.
  - Test again using http://localhost:8081/client/events and then using the CQL client try to issue first a `describe tables` statement to see if the table was actually created and then a
  `select * from events` to check the persisted events, if any.
  - Congratulations, you just saved the events in a Cassandra table! Let's suppose now that we cannot insert the events one by one but we need to add them to some kind of a buffer and then
  insert a bulk of them, for example each 10. For this you have to change your BaseSubscriber implementation. What fields would you need to add to your subscriber in order
  to implement such a feature?

  Start the subscription by requesting 10 elements instead of 1. Now go to `hookOnNext` and increment the counter each time an event is received and then add the event to the buffer list.
  Add an if element to check the counter value, as soon as it reaches 10 call the (not existent yet) `.saveBulkEvents()` method from your service instance and then request the next bunch of
  events by calling `request(10)`. Do not forget to clear the buffer afterwards.
  - Now let's suppose we have another problem. The events we saved were the latest at the time the method was called and it might that they're already obsolete. What if we would like the data
  we have in the database to expire and disappear by itself so we can refresh it ? Cassandra offers us exact the feature we need for this - the time to live, or TTL. The TTL
  can be set at a row or field level or as default for a table. We'll use for our example a TTL set per row.

  Because we insert the events in bulk we'll have to do a little trick. Instead of using our repository class to insert the data you'll have to use the `CassandraTemplate` directly. To do so
  inject a field of type `CassandraOperations` (the interface `CassandraTemplate` is implementing) in `TrackingEventClientService`
  Edit the `saveBulkEvents` method and instead of calling the `saveAll` method on the repository call `batchOps().(...)`. Create an `WriteOptions`
  instance with the desired TTL (let's say 60 seconds) and do not forget to call execute in the end.
  - Manually test again using http://localhost:8081/client/events and check if the events were persisted in the database. Wait 60 seconds or the duration you set and then issue
  again a select against the events table. Notice how the table is empty.

  - Let's go back to the generator service and extend our fake event generator method so we can have more event types per id, this we'll allow us on the client side to try
  to group them by id and maybe to sort them. Go to `EventController` and add a method to return let's say the top 100 (whatever that means) events with the following signature:
  ```java
  @GetMapping(path = "/top", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<TrackingEvent> getTopEvents()
  ```
  - You can start by using the Flux.range operator. How would you proceed further ?
  - Go to the client service and implement a method to read the grouped events using the reactive web client. What operator could you use to read the events in a grouped manner ?

### Reactive Cassandra
 The datastax driver is not yet reactive (see https://datastax-oss.atlassian.net/browse/JAVA-1342), so all what reactive-cassandra does is to bridge between reactive types and the usual imperative world.
  - Reactive repository methods must return Mono or Flux, resolved types not supported. On the other hand, parameters can be both, query creation and execution is deferred until subscription.

 Let's try to convert the already created repository class to a reactive repository.
 1. Add `spring-boot-starter-data-cassandra-reactive` as a dependency to your project.
 2. Add the `@EnableReactiveCassandraRepositories` annotation to your cassandra configuration class.
 3. Extend the `ReactiveCassandraRepository` class instead of `CassandraRepository` and change the return types to their reactive counterpart.
 4. How does your service change ?

## Extra material 1.
 Exercise: after reading the events from the generator service try to display them using a HTML template. Add a filter field to be able to choose the event type you want to list.
 Hint: use the `Freemarker` templating engine and the spring boot support for it. Add a form element to your template and call a PUT method on your controller to update the information in the database. Try to use the set or map features of Cassandra.
 Read the event's details by calling another reactive rest service method when you click one item from the list.

## Extra material 2.

Visualise the data in real time using Javascript. For our example we'll use the CanvasJS library.

1. Add `Freemarker` as a dependency using the following maven snippet:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```
2. Add a `Freemarker` template under 'resources/templates' so that spring boot will be able to find it. The template must have the .ftl extension.
3. Add a rest method in your controller to generate and deliver the template as a html resource.
4. Add the following HTML to your template:

```html
<!DOCTYPE HTML>
<html>
<head>
    <script>
window.onload = function() {

    var dataPoints = [];

    var chart = new CanvasJS.Chart("chartContainer", {
        theme: "light2",
        title: {
            text: "Live Data"
        },
        data: [{
            type: "line",
            dataPoints: dataPoints
        }]
    });

    var jsonStream = new EventSource('http://localhost:8080/events')
    jsonStream.onmessage = function (e) {
       var message = JSON.parse(e.data);
       addData(message);
    };

    // Initial Values
    var xValue = 0;
    var yValue = 10;
    var newDataCount = 6;

    function addData(data) {
        if(newDataCount != 1) {
            $.each(data, function(key, value) {
                console.log("each called one time " + data);
                if(key == "eventValue") {
                    console.log("key " + key + "value " + value + " parse is " + parseFloat(value));
                    dataPoints.push({x: value[0], y: parseFloat(value)});
                    xValue++;
                    yValue = parseFloat(value);
                }
            });
        } else {
            console.log(data.eventValue);
            dataPoints.push({x: xValue, y: parseFloat(data.eventValue)});
            xValue++;
            yValue = parseFloat(data.eventValue);
        }

        newDataCount = 1;
        chart.render();
        //setTimeout(updateData, 1500);
    }

}
</script>
</head>
<body>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
</body>
</html>
```

5. Restart your application and refresh your page, you should see a javascript chart being updated real time!


## The end
Congratulations, you finished the workshop!