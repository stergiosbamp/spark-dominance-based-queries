import numpy as np
import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000
DIM = 2

rng = default_rng(1)

# Create samples from a normal (Gaussian) distribution
# Mean of the distribution is 0.5, and std is chosen as 0.5/3. so that
# 99,74% of the generated coordinates are within the [0,1] range
# (the extremes of the range correspond to 3 standard deviations).
x = rng.normal(loc=0.5, scale=0.5/3., size=(NUM_SAMPLES, DIM))

plt.scatter(x[:, 0], x[:, 1])
plt.show()

# save the data into csv file with 5 decimals
np.savetxt("{}d-normal-{}.csv".format(DIM, NUM_SAMPLES), x, delimiter=",", fmt="%.5f")
