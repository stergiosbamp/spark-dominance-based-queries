import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000
DIM = 2

rng = default_rng(1)

# prepare data
data1 = 20 * rng.standard_normal(size=(NUM_SAMPLES, DIM)) + 100
data2 = data1 + (10 * rng.standard_normal(size=(NUM_SAMPLES, DIM)) + 50)

# plot
plt.scatter(data1, data2)
plt.show()
