import cv2 #computer vision - for loading and processing images
import numpy as np
import tensorflow as tf #for ml part

#loading hand written digits dataset from mnist. mnist contains over 70000 handwritten images of digits
mnist = tf.keras.datasets.mnist

#splitting dataset into training and testing data
#x_train = represting pixel data - the image itself
#y_train = representing classification data - classifying the image as a digit
(x_train , y_train) , (x_test , y_test) = mnist.load_data()

#shape of training and testing data
#print(x_train.shape)
#print(x_test.shape)
#normalizing data - scaling the data so it falls between 0 and 1. (scaling pixels)
x_train = tf.keras.utils.normalize(x_train , axis = 1) # axis = 1 because to normalize only pixels and not digits
x_test = tf.keras.utils.normalize(x_test , axis = 1)


#working on neural network
#Using Sequential model - it is appropriate for a plain stack of layers where each layer has exactly one input tensor and one output tensor.
model = tf.keras.models.Sequential()
#add layers to model
#flatten layer - educes the input data into a single dimension instead of 2 dimensions. While doing so, it does not affect the batch size.
model.add(tf.keras.layers.Flatten(input_shape=(28,28))) #hence reducing it into 784 instead of (28,28)
# 3 dense layers
# Dense layer - is a basic layer where each nueron is connected to each other nueron
model.add(tf.keras.layers.Dense(128 , activation = 'relu')) #relu - rectified linear unit. The function returns 0 if the input is negative, but for any positive input, it returns that value back.  it does not activate all the neurons at the same time
model.add(tf.keras.layers.Dense(128 , activation = 'relu'))
model.add(tf.keras.layers.Dense(10 , activation = 'softmax')) #output layer.  represents individual digits 0-9. softmax = is often used as the activation for the last layer of a classification network because the result could be interpreted as a probability distribution. It makes sure all 10 nuerons adds up to 1
#softmax also indicates the confidence level - ex - if digit is correctly recognized,  the value might be around ~0.9 for 2 and vlow value for other 9 digits
#compile the model
#adam optimizer involves a combination of two gradient descent methodologies: Momentum: This algorithm is used to accelerate the gradient descent algorithm by taking into consideration the 'exponentially weighted average' of the gradients. Using averages makes the algorithm converge towards the minima in a faster pace.
#sparse categorical cross-entropy is used when true labels are one-hot encoded, for example, we have the following true values for 3-class classification problem [1,0,0] , [0,1,0] and [0,0,1]. In sparse categorical cross-entropy , truth labels are integer encoded, for example, [1] , [2] and [3] for 3-class problem
model.compile(optimizer = 'adam' , loss = 'sparse_categorical_crossentropy' , metrics = ['accuracy'])

#train model
model.fit(x_train , y_train , epochs = 1) #epochs  - number of training iterations.

#evaluate the model to see how it performs on training and testing data
loss , accuracy = model.evaluate(x_test , y_test)

#to read the saved image in a grey scale
img=cv2.imread("C:\\Users\\Dell\\OneDrive\\Desktop\\INFO_5100\\numberRecognitionJavaFx\\number.png")[:,:,0] #reading the image using computer vision cv2. we don't require color of image, hence getting only b/w [:,:,0]

#reshape the image to 28 * 28 pixel as tensorflow understands only 28*28 pixel format images
img2 = np.reshape(img, (-1, 28, 28))

#predict the actual image
prediction = model.predict(img2)

#print the output
print(f"The number maybe be : {np.argmax(prediction)}")
