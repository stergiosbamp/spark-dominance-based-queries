import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000

rng = default_rng(1)

# prepare data
data1 = 20 * rng.standard_normal(NUM_SAMPLES) + 100
data2 = data1 + (10 * rng.standard_normal(NUM_SAMPLES) + 50)

# plot
plt.scatter(data1, data2)
plt.show()
